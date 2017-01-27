package com.gitlab.daring.sandbox.image.processor;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.function.Consumer;

public class ImageProcessorUtils {

	public static ImageProcessor newProc(Consumer<Mat> c) {
		return m -> { c.accept(m); return m; };
	}

	private ImageProcessorUtils() {
	}

}
