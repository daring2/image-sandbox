package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.CommandScript;
import com.gitlab.daring.image.concurrent.TaskExecutor;

import java.util.List;

import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.util.Collections.emptyList;
import static one.util.streamex.IntStreamEx.range;

class ScriptExecutor implements AutoCloseable {

    final ImageSandbox sb;
    final CommandScript script;
    final CommandEnv env;
    final TaskExecutor taskExec = new TaskExecutor();

    List<String> files = emptyList();

    ScriptExecutor(ImageSandbox sb) {
        this.sb = sb;
        script = sb.mp.script;
        env = script.env;
    }

    void executeAsync() {
        taskExec.executeAsync(this::execute);
    }

    void execute() {
        files = splitAndTrim(sb.mp.filesParam.getValue(), ",").toList();
        if (files.isEmpty()) {
            script.execute();
            return;
        }
        range(files.size()).forEach(this::runScript);
        script.runTask("combine");
    }

    void runScript(int i) {
        String file = files.get(i);
        env.putVar("i", i).putVar("file", file);
        env.setTask("");
        script.runCommand("read", file);
        script.execute();
        env.putMat("$i", env.mat);
    }

    @Override
    public void close() {
        taskExec.close();
    }

}