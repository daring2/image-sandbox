package com.gitlab.daring.image.command;

import org.bytedeco.javacpp.opencv_core.Mat;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.lang.Math.max;

public class CommandUtils {

	public static Command newCommand(Consumer<Mat> c) {
		return env -> c.accept(env.mat);
	}

	public static Command newCommand(BiConsumer<Mat, Mat> c) {
		Mat d = new Mat();
		return env -> { c.accept(env.mat, d); env.mat = d; };
	}

	public static String[] parseParams(String paramStr, List<String> defParams) {
		List<String> ss = splitAndTrim(paramStr, ",");
		String[] ps = new String[max(ss.size(), defParams.size())];
		for (int i = 0; i < ps.length; i++)
			ps[i] = i < ss.size() ? ss.get(i) : defParams.get(i);
		return ps;
	}

	private CommandUtils() {
	}

}
