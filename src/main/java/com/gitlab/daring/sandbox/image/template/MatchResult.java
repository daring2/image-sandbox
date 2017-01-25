package com.gitlab.daring.sandbox.image.template;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core.Point;

public class MatchResult {

	final Point point = new Point();
	final DoublePointer valueRef = new DoublePointer(1);

	public Point getPoint() {
		return point;
	}

	public double getValue() {
		return valueRef.get();
	}

}
