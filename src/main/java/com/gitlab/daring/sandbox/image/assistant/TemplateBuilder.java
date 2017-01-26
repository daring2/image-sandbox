package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;
import javax.annotation.concurrent.NotThreadSafe;
import static org.bytedeco.javacpp.opencv_imgproc.*;

@NotThreadSafe
class TemplateBuilder extends BaseComponent {

	final double binThreshold = config.getDouble("binThreshold");

	final Mat m = new Mat();

	TemplateBuilder(ShotAssistant a) {
		super(a.config.getConfig("template"));
	}

	Mat build(Mat inputMat) {
		cvtColor(inputMat, m, COLOR_BGR2GRAY);
		morphologyEx(m, m, MORPH_GRADIENT, new Mat());
		threshold(m, m, binThreshold, 255, THRESH_BINARY);
		return m;
	}

}
