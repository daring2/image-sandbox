package com.gitlab.daring.sandbox.image.processor.impl;

import com.gitlab.daring.sandbox.image.processor.BaseImageProcessor;
import org.bytedeco.javacpp.opencv_core.Mat;
import static java.lang.Double.parseDouble;
import static org.bytedeco.javacpp.opencv_imgproc.Canny;

public class CannyProcessor extends BaseImageProcessor {

	final double th1 = parseDouble(args[0]);
	final double th2 = parseDouble(args[2]);

	public CannyProcessor(String[] args) {
		super(args);
	}

	@Override
	public Mat process(Mat m) {
		Canny(m, m, th1, th2);
		return m;
	}

}
