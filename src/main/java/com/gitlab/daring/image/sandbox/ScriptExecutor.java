package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.CommandScript;
import com.gitlab.daring.image.concurrent.TaskExecutor;

import java.util.List;

import static com.gitlab.daring.image.command.CommandScriptUtils.cmdStr;
import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.util.Collections.emptyList;
import static one.util.streamex.IntStreamEx.range;
import static org.apache.commons.lang3.StringUtils.replace;

class ScriptExecutor implements AutoCloseable {

	final ImageSandbox sb;
	final CommandScript cmdScript;
	final CommandEnv cmdEnv;
	final TaskExecutor executor = new TaskExecutor();

	String script = "";
	List<String> files = emptyList();

	ScriptExecutor(ImageSandbox sb) {
		this.sb = sb;
		cmdScript = sb.mp.script;
		cmdEnv = cmdScript.env;
	}

	void executeAsync() {
		executor.executeAsync(this::execute);
	}

	void execute() {
		files = splitAndTrim(sb.mp.filesParam.v, ",").toList();
		if (files.isEmpty()) {
			cmdScript.execute();
			return;
		}
		script = cmdScript.getText();
		range(files.size()).forEach(this::runScript);
		cmdScript.runTask("combine");
		cmdScript.setText(script);
	}

	void runScript(int i) {
		String file = files.get(i);
		String sc = script;
		sc = cmdStr("read", file) + sc;
		sc = replace(sc, "$file", file);
		sc = replace(sc, "$i", "" + i);
		cmdScript.execute(sc, "");
		cmdEnv.putMat("" + i, cmdEnv.mat);
	}

	@Override
	public void close() {
		executor.close();
	}

}