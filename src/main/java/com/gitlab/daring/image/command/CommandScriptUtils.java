package com.gitlab.daring.image.command;

import org.bytedeco.javacpp.opencv_core.Mat;

import static java.lang.String.join;

public class CommandScriptUtils {

	public static ScriptCommand parseScript(String script) {
		return CommandRegistry.Instance.parseScript(script);
	}

	public static void runScript(String script) {
		parseScript(script).execute(localEnv());
	}

	public static Mat runCommand(String cmd, String... args) {
		runScript(cmd + "(" + join(", ", args) + ")");
		return localEnv().mat;
	}

	public static CommandEnv localEnv() {
		return CommandEnv.local.get();
	}

	private CommandScriptUtils() {
	}

}