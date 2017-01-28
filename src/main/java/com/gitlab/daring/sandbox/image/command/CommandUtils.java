package com.gitlab.daring.sandbox.image.command;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.List;
import java.util.function.Consumer;

import static com.gitlab.daring.sandbox.image.util.ExtStringUtils.splitAndTrim;
import static java.lang.Math.max;

public class CommandUtils {

	public static Command newCommand(Consumer<Mat> c) {
		return env -> c.accept(env.mat);
	}

	public static String[] parseArgs(String argStr, List<String> defArgs) {
		List<String> ss = splitAndTrim(argStr, ",");
		String[] args = new String[max(ss.size(), defArgs.size())];
		for (int i = 0; i < args.length; i++)
			args[i] = i < ss.size() ? ss.get(i) : defArgs.get(i);
		return args;
	}

	private CommandUtils() {
	}

}
