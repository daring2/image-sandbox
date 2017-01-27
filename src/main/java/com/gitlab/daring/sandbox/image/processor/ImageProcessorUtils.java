package com.gitlab.daring.sandbox.image.processor;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.List;
import java.util.function.Consumer;

import static java.lang.Math.max;
import static org.apache.commons.lang3.StringUtils.split;

public class ImageProcessorUtils {

	public static ImageProcessor newProc(Consumer<Mat> c) {
		return m -> { c.accept(m); return m; };
	}

	public static String[] parseArgs(String argStr, List<String> defArgs) {
		String[] ss = split(argStr, ", ");
		String[] args = new String[max(ss.length, defArgs.size())];
		for (int i = 0; i < args.length; i++)
			args[i] = i < ss.length ? ss[i] : defArgs.get(i);
		return args;
	}

	private ImageProcessorUtils() {
	}

}
