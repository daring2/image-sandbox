package com.gitlab.daring.image.command.combine;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;

public enum CombineMethod {

	MAX(opencv_core::max),
	MIN(opencv_core::min),
	ADD(opencv_core::add),
	SUBTRACT(opencv_core::subtract),
	AND(opencv_core::bitwise_and),
	OR(opencv_core::bitwise_or),
	XOR(opencv_core::bitwise_xor);

	final CombineFunction func;

	CombineMethod(CombineFunction func) {
		this.func = func;
	}

	@FunctionalInterface
	interface CombineFunction {
		void apply(Mat m1, Mat m2, Mat dm);
	}

}