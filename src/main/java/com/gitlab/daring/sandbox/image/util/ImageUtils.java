package com.gitlab.daring.sandbox.image.util;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.function.Consumer;

public class ImageUtils {

	public static Mat buildMat(Consumer<Mat> func) {
		Mat m = new Mat();
		func.accept(m);
		return m;
	}

	private ImageUtils() {
	}

}
