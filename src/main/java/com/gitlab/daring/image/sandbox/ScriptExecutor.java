package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandScript;

import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

class ScriptExecutor implements AutoCloseable {

	final ImageSandbox sb;
	final CommandScript script;

	final ExecutorService executor = newSingleThreadExecutor();

	ScriptExecutor(ImageSandbox sb) {
		this.sb = sb;
		script = sb.mp.script;
	}

	void execute() {
		List<String> files = splitAndTrim(sb.mp.filesParam.v, ",").toList();
		if (files.isEmpty()) files.add("");
		files.forEach(this::execute);
	}

	void execute(String file) {
		script.env.mat = imread(file);
		script.setText(script.getText()); //TODO refactor
		script.executeAsync(executor);
	}

	@Override
	public void close() {
		executor.shutdownNow();
	}

}