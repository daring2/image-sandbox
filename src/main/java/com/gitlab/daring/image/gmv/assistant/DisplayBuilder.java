package com.gitlab.daring.image.gmv.assistant;

import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.*;

import static com.gitlab.daring.image.util.ImageUtils.addWeightedMat;
import static org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.BLUE;
import static org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.GREEN;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

class DisplayBuilder extends BaseComponent {

	final ShotAssistant a;
	final IntParam sampleOpacity = new IntParam("0:Образец:0-100");
	final IntParam templateOpacity = new IntParam("0:Контур:0-100");

	DisplayBuilder(ShotAssistant a) {
		super(a.config.getConfig("display"));
		this.a = a;
		sampleOpacity.bind(config, "sampleOpacity");
		templateOpacity.bind(config, "templateOpacity");
	}

	void build(Mat inputMat) {
		Mat dm = a.displayMat;
		inputMat.copyTo(dm);
		if (!a.templateMat.empty()) {
			addWeightedMat(dm, a.sampleMat, dm, sampleOpacity.pv());
			MatExpr dt = multiply(a.templateMat, templateOpacity.pv());
			max(dm, dt.asMat(), dm);
		} else {
			inputMat.copyTo(dm);
		}
		Scalar rc = a.checkResult ? GREEN : BLUE;
		rectangle(dm, a.positionControl.roi, rc, 2, LINE_8, 0);
	}

}
