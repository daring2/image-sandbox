package com.gitlab.daring.sandbox.image.processor;

import org.bytedeco.javacpp.opencv_core.Mat;

public interface ImageProcessor {

	Mat process(Mat m);

	@FunctionalInterface
	interface Factory {
		ImageProcessor create(String[] args);
	}

}
