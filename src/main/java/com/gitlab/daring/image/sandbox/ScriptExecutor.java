package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandScript;

import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

class ScriptExecutor implements AutoCloseable {

	final ImageSandbox sb;
	final CommandScript cmdScript;

	final ExecutorService executor = newSingleThreadExecutor();
	String script;

	ScriptExecutor(ImageSandbox sb) {
		this.sb = sb;
		cmdScript = sb.mp.script;
	}

	void executeAsync() {
		cmdScript.executeAsync(executor, this::execute);
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
		executor.shutdownNow();
	}

}