package com.gitlab.daring.image.command.transform;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;

public enum  BitwiseOperation {

	AND(opencv_core::bitwise_and),
	OR(opencv_core::bitwise_or),
	NOT(opencv_core::bitwise_not),
	XOR(opencv_core::bitwise_xor);

	final BitwiseFunction func;

	BitwiseOperation(BitwiseFunction func) {
		this.func = func;
	}

	@FunctionalInterface
	interface BitwiseFunction {
		void apply(Mat m1, Mat m2, Mat dm);
	}

}
