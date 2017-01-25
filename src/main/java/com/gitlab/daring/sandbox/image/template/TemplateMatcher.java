package com.gitlab.daring.sandbox.image.template;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import com.typesafe.config.Config;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import javax.annotation.concurrent.NotThreadSafe;
import static com.gitlab.daring.sandbox.image.util.ConvertUtils.toJava;
import static org.bytedeco.javacpp.opencv_core.minMaxLoc;
import static org.bytedeco.javacpp.opencv_imgproc.matchTemplate;

@NotThreadSafe
public class TemplateMatcher extends BaseComponent {

	final Mat resultMat = new Mat();
	final MatchMethod method = config.getEnum(MatchMethod.class, "method");
	final DoublePointer valueRef = new DoublePointer(1);
	final Point pointRef = new Point();

	public TemplateMatcher(Config config) {
		super(config);
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
