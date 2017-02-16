package com.gitlab.daring.image.command;

import org.bytedeco.javacpp.opencv_core.Mat;

import static org.apache.commons.lang3.StringUtils.join;

public class CommandScriptUtils {

	public static ScriptCommand parseScript(String script) {
		return CommandRegistry.Instance.parseScript(script);
	}

	public static void runScript(String script) {
		parseScript(script).execute(localEnv());
	}

	public static String cmdStr(String cmd, Object... args) {
		return cmd + "(" + join(args, ", ") + ");";
	}

	public static Mat runCommand(String cmd, Object... args) {
		runScript(cmdStr(cmd, args));
		return localEnv().mat;
	}

	public static CommandEnv localEnv() {
		return CommandEnv.local.get();
	}

	private CommandScriptUtils() {
	}

}