package com.gitlab.daring.image.command.combine;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;

public enum CombineMethod {

	Max(opencv_core::max),
	Min(opencv_core::min),
	Add(opencv_core::add),
	Subtract(opencv_core::subtract),
	AbsDiff(opencv_core::absdiff),
	And(opencv_core::bitwise_and),
	Or(opencv_core::bitwise_or),
	Xor(opencv_core::bitwise_xor);

	final CombineFunction func;

	CombineMethod(CombineFunction func) {
		this.func = func;
	}

	@FunctionalInterface
	interface CombineFunction {
		void apply(Mat m1, Mat m2, Mat dm);
	}

}
