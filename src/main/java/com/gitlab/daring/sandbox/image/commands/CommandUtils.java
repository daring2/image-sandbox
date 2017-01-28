package com.gitlab.daring.sandbox.image.commands;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.List;
import java.util.function.Consumer;

import static java.lang.Math.max;
import static org.apache.commons.lang3.StringUtils.split;

public class CommandUtils {

	public static Command newCommand(Consumer<Mat> c) {
		return env -> c.accept(env.mat);
	}

	public static String[] parseArgs(String argStr, List<String> defArgs) {
		String[] ss = split(argStr, ", ");
		String[] args = new String[max(ss.length, defArgs.size())];
		for (int i = 0; i < args.length; i++)
			args[i] = i < ss.length ? ss[i] : defArgs.get(i);
		return args;
	}

	private CommandUtils() {
	}

}
