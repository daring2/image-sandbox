package com.gitlab.daring.image.command;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.List;
import java.util.function.Consumer;

import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.lang.Math.max;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public class CommandUtils {

	public static final Command EmptyCommand = newCommand(m -> {});

	public static Command newCommand(Consumer<Mat> c) {
		return env -> c.accept(env.mat);
	}

	public static List<String> splitScript(String script) {
		return splitAndTrim(script, "\n").stream().filter(l -> !l.startsWith("//"))
			.flatMap(l -> splitAndTrim(l, ";").stream()).filter(c -> !c.startsWith("-"))
			.collect(toList());
	}

	public static String[] parseArgs(String argStr, List<String> defArgs) {
		List<String> ss = splitAndTrim(argStr, ",");
		return buildArgs(ss, defArgs);
	}

	public static String[] buildArgs(List<String> args, List<String> defArgs) {
		String[] ps = new String[max(args.size(), defArgs.size())];
		for (int i = 0; i < ps.length; i++)
			ps[i] = i < args.size() ? args.get(i) : defArgs.get(i);
		return ps;
	}

	public static int[] parseIntParams(String... ps) {
		return stream(ps).mapToInt(Integer::parseInt).toArray();
	}

	private CommandUtils() {
	}

}
