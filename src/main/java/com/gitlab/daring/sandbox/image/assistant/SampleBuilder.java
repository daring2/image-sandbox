package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_imgproc.*;

@SuppressWarnings("WeakerAccess")
class SampleBuilder extends BaseComponent {

	final double threshold = config.getDouble("threshold");

	final Mat m = new Mat();

	SampleBuilder(ShotAssistant a) {
		super(a.config.getConfig("SampleBuilder"));
	}

	Mat build(Mat inputMat) {
		cvtColor(inputMat, m, COLOR_BGR2GRAY);
		morphologyEx(m, m, MORPH_GRADIENT, new Mat());
		threshold(m, m, threshold, 255, THRESH_BINARY);
		return m;
	}

}
