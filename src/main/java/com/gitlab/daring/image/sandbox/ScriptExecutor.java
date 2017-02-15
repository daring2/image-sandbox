package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandScript;
import com.gitlab.daring.image.concurrent.TaskExecutor;

import java.util.List;

import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;

class ScriptExecutor implements AutoCloseable {

	final ImageSandbox sb;
	final CommandScript cmdScript;
	final TaskExecutor executor = new TaskExecutor();
	String script;

	ScriptExecutor(ImageSandbox sb) {
		this.sb = sb;
		cmdScript = sb.mp.script;
	}

	void executeAsync() {
		executor.executeAsync(this::execute);
	}

	void execute() {
		List<String> files = splitAndTrim(sb.mp.filesParam.v, ",").toList();
		if (files.isEmpty()) files.add("");
		script = cmdScript.getText();
		files.forEach(this::execute);
		cmdScript.setText(script);
	}

	void execute(String file) {
		String readCmd = !file.isEmpty() ? "read(" + file + ")\n" : "";
		cmdScript.execute(readCmd + script);
	}

	@Override
	public void close() {
		executor.close();
	}

}