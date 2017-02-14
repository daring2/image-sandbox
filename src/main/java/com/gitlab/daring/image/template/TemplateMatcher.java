package com.gitlab.daring.image.template;

import com.typesafe.config.Config;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;

import javax.annotation.concurrent.NotThreadSafe;

import static com.gitlab.daring.image.util.EnumUtils.findEnum;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static org.bytedeco.javacpp.opencv_core.minMaxLoc;
import static org.bytedeco.javacpp.opencv_imgproc.matchTemplate;

@NotThreadSafe
public class TemplateMatcher {

	public MatchMethod method = MatchMethod.CCORR_NORMED;

	final Mat resultMat = new Mat();
	final DoublePointer valueRef = new DoublePointer(1);
	final Point pointRef = new Point();

	public TemplateMatcher() {
	}

	public TemplateMatcher(Config c) {
		method = findEnum(c, MatchMethod.class, "method");
	}

	public MatchResult findBest(Mat mat, Mat templ) {
		matchTemplate(mat, templ, resultMat, method.ordinal());
		if (method.isMinBest()) {
			minMaxLoc(resultMat, valueRef, null, pointRef, null, new Mat());
		} else {
			minMaxLoc(resultMat, null, valueRef, null, pointRef, new Mat());
		}
		return new MatchResult(toJava(pointRef), valueRef.get());
	}

}
