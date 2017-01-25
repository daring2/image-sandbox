package com.gitlab.daring.sandbox.image.template;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import com.typesafe.config.Config;
import org.bytedeco.javacpp.opencv_core.Mat;
import javax.annotation.concurrent.NotThreadSafe;
import static org.bytedeco.javacpp.opencv_core.minMaxLoc;
import static org.bytedeco.javacpp.opencv_imgproc.matchTemplate;

@NotThreadSafe
public class TemplateMatcher extends BaseComponent {

	final Mat resultMat = new Mat();
	final MatchMethod method = config.getEnum(MatchMethod.class, "method");

	public TemplateMatcher(Config config) {
		super(config);
	}

	public MatchResult findBest(Mat mat, Mat templ) {
		matchTemplate(mat, templ, resultMat, method.ordinal());
		MatchResult r = new MatchResult();
		if (method.isMinBest()) {
			minMaxLoc(resultMat, r.valueRef, null, r.point, null, new Mat());
		} else {
			minMaxLoc(resultMat, null, r.valueRef, null, r.point, new Mat());
		}
		return r;
	}

}
