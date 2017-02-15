package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandScript;
import com.gitlab.daring.image.concurrent.TaskExecutor;

import java.util.List;

import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.util.Collections.emptyList;
import static one.util.streamex.IntStreamEx.range;
import static org.apache.commons.lang3.StringUtils.replace;

class ScriptExecutor implements AutoCloseable {

	final ImageSandbox sb;
	final CommandScript cmdScript;
	final TaskExecutor executor = new TaskExecutor();

	String script = "";
	List<String> files = emptyList();

	ScriptExecutor(ImageSandbox sb) {
		this.sb = sb;
		cmdScript = sb.mp.script;
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
		cmdScript.setText(script);
	}

	void runScript(int i) {
		String file = files.get(i);
		String sc = script;
		if (!file.isEmpty()) sc = "read(" + file + ")\n" + sc;
		sc = replace(sc, "$fileIndex", "" + i);
		sc = replace(sc, "$file", file);
		cmdScript.execute(sc);
	}

	@Override
	public void close() {
		executor.close();
	}

}