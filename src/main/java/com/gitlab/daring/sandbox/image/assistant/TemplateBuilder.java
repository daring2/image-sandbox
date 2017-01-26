package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;
import javax.annotation.concurrent.NotThreadSafe;
import static com.gitlab.daring.sandbox.image.util.ImageUtils.convertToGrey;
import static org.bytedeco.javacpp.opencv_imgproc.*;

@NotThreadSafe
class TemplateBuilder extends BaseComponent {

	final double binThreshold = config.getDouble("binThreshold");

	TemplateBuilder(ShotAssistant a) {
		super(a.config.getConfig("template"));
	}

	Mat build(Mat inputMat) {
		Mat m = new Mat();
		convertToGrey(inputMat, m);
		morphologyEx(m, m, MORPH_GRADIENT, new Mat());
		threshold(m, m, binThreshold, 255, THRESH_BINARY);
		return m;
	}

}
