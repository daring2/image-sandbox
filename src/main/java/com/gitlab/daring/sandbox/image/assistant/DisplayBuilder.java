package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.*;

import static com.gitlab.daring.sandbox.image.util.ImageUtils.addWeightedMat;
import static com.gitlab.daring.sandbox.image.util.ImageUtils.buildMat;
import static org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.BLUE;
import static org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.GREEN;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

class DisplayBuilder extends BaseComponent{

	final ShotAssistant a;

	double sampleOpacity;
	double templateOpacity;
	boolean flip;

	DisplayBuilder(ShotAssistant a) {
		super(a.config.getConfig("display"));
		this.a = a;
		this.flip = config.getBoolean("flip");
	}

	void build(Mat inputMat) {
		Mat dm = a.displayMat;
		inputMat.copyTo(dm);
		if (!a.templateMat.empty()) {
			addWeightedMat(dm, a.sampleMat, dm, sampleOpacity);
			opencv_core.MatExpr dt = multiply(a.templateMat, templateOpacity);
			bitwise_or(dm, dt.asMat(), dm);
		} else {
			inputMat.copyTo(dm);
		}
		rectangle(dm, a.control.roi, a.checkResult ? GREEN : BLUE);
		if (flip) {
			buildMat(m -> flip(dm, m, 1)).copyTo(dm);
		}
	}

}
