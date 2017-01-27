package com.gitlab.daring.sandbox.image.processor.impl;

import com.gitlab.daring.sandbox.image.processor.BaseImageProcessor;
import org.bytedeco.javacpp.opencv_core.Mat;

import static com.gitlab.daring.sandbox.image.util.EnumUtils.findEnumIndex;
import static org.bytedeco.javacpp.opencv_imgproc.morphologyEx;

public class MorphologyProcessor extends BaseImageProcessor {

	final int operation = findEnumIndex(Operation.values(), args[0]);
	final Mat kernel = new Mat();

	public MorphologyProcessor(String[] args) {
		super(args);
	}

	@Override
	public Mat process(Mat m) {
		morphologyEx(m, m, operation, kernel);
		return m;
	}

	enum Operation {
		Erode, Dilate, Open, Close, Gradient, TopHat, BlackHat, HitMiss
	}

}
