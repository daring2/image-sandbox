package com.gitlab.daring.image.command.structure;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.EnumMap;

public class Contour {

	final Mat mat;
	final EnumMap<ContourMetric, Double> metrics = new EnumMap<>(ContourMetric.class);

	public Contour(Mat mat) {
		this.mat = mat;
	}

	public double getMetric(ContourMetric m) {
		return metrics.computeIfAbsent(m, mk -> m.calculate(mat));
	}

}
