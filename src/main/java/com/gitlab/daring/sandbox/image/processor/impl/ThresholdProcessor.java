package com.gitlab.daring.sandbox.image.processor.impl;

import com.gitlab.daring.sandbox.image.processor.BaseImageProcessor;
import org.bytedeco.javacpp.opencv_core.Mat;

import static com.gitlab.daring.sandbox.image.util.EnumUtils.findEnumIndex;
import static java.lang.Double.parseDouble;
import static org.bytedeco.javacpp.opencv_imgproc.threshold;

public class ThresholdProcessor extends BaseImageProcessor {

	final double th1 = parseDouble(args[0]);
	final double maxValue = parseDouble(args[1]);
	final int type = findEnumIndex(Type.values(), args[2]);

	public ThresholdProcessor(String[] args) {
		super(args);
	}

	@Override
	public Mat process(Mat m) {
		threshold(m, m, th1, maxValue, type);
		return m;
	}

	enum Type {
		Bin, BinInv, Trunc, ToZero, ToZeroInv
	}

}
