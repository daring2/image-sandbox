package com.gitlab.daring.sandbox.image.processor;

import org.bytedeco.javacpp.opencv_core.Mat;

@FunctionalInterface
public interface ImageProcessor {

	Mat process(Mat m);

	@FunctionalInterface
	interface Factory {
		ImageProcessor create(String[] args);
	}

}
