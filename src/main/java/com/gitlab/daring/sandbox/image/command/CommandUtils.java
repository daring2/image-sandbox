package com.gitlab.daring.sandbox.image.command;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.Math.max;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.split;

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

	public static List<String> splitAndTrim(String str, String sepChars) {
		return Arrays.stream(split(str, sepChars))
			.map(String::trim).filter(s -> !s.isEmpty())
			.collect(toList());
	}

	private CommandUtils() {
	}

}
