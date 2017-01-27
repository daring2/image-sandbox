package com.gitlab.daring.sandbox.image.processor;

import org.bytedeco.javacpp.opencv_core.Mat;

import static com.gitlab.daring.sandbox.image.processor.ImageProcessorUtils.newProc;
import static com.gitlab.daring.sandbox.image.util.EnumUtils.findEnumIndex;
import static java.lang.Double.parseDouble;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class TransformProcessors {

	public static void register(ImageProcessorRegistry r) {
		TransformProcessors p = new TransformProcessors();
		r.register("threshold", p::newThresholdProc);
		r.register("morphology", p::newMorphologyProc);
		r.register("canny", p::newCannyProc);
	}

	public ImageProcessor newThresholdProc(String[] args) {
		double th1 = parseDouble(args[0]);
		double maxValue = parseDouble(args[1]);
		int type = findEnumIndex(ThresholdType.values(), args[2]);
		return newProc(m -> threshold(m, m, th1, maxValue, type));
	}

	enum ThresholdType {
		Bin, BinInv, Trunc, ToZero, ToZeroInv
	}

	public ImageProcessor newMorphologyProc(String[] args) {
		int op = findEnumIndex(MorphOperation.values(), args[0]);
		Mat kernel = new Mat();
		return newProc(m -> morphologyEx(m, m, op, kernel));
	}

	enum MorphOperation {
		Erode, Dilate, Open, Close, Gradient, TopHat, BlackHat, HitMiss
	}

	public ImageProcessor newCannyProc(String[] args) {
		double th1 = parseDouble(args[0]);
		double th2 = parseDouble(args[1]);
		return newProc(m -> Canny(m, m, th1, th2));
	}

}
