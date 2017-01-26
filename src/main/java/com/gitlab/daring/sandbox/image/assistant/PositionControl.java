package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import com.gitlab.daring.sandbox.image.template.MatchResult;
import com.gitlab.daring.sandbox.image.template.TemplateMatcher;
import com.google.common.primitives.Doubles;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;
import static com.gitlab.daring.sandbox.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.sandbox.image.util.ImageUtils.resizeMat;
import static com.gitlab.daring.sandbox.image.util.ImageUtils.rotateMat;

@NotThreadSafe
class PositionControl extends BaseComponent {

	final ShotAssistant assistant;
	final double rectSize = config.getDouble("rectSize");
	final TemplateMatcher matcher = new TemplateMatcher(getConfig("matcher"));
	final PositionLimits limits = new PositionLimits(getConfig("limits"));

	final Rect roi;
	final Rectangle pos;
	final Mat template = new Mat();
	double minValue = limits.minValue;

	PositionControl(ShotAssistant a) {
		super(a.config.getConfig("position"));
		assistant = a;
		roi = getCenterRect(a.getSize(), rectSize);
		pos = limits.buildPositionRect(roi.x(), roi.y());
	}

	void setTemplate(Mat mat) {
		new Mat(mat, roi).copyTo(template);
		MatchResult r1 = findMatch(resizeMat(mat, limits.scale));
		MatchResult r2 = findMatch(rotateMat(mat, limits.angle));
		minValue = Doubles.min(limits.minValue, r1.value, r2.value);
	}

	MatchResult findMatch(Mat mat) {
		Mat sm = assistant.templateBuilder.build(mat);
		return matcher.findBest(sm, template);
	}

	boolean check(Mat mat) {
		MatchResult mr = findMatch(mat);
		assistant.statusField.setText("result: " + mr.value); //TODO refactor
		return mr.value > minValue && pos.contains(mr.point);
	}

}
