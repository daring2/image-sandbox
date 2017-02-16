package com.gitlab.daring.image.assistant;

import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.common.BaseComponent;
import com.gitlab.daring.image.template.MatchResult;
import com.gitlab.daring.image.template.TemplateMatcher;
import com.google.common.primitives.Doubles;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;

import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.ImageUtils.resizeMat;
import static com.gitlab.daring.image.util.ImageUtils.rotateMat;

@NotThreadSafe
class PositionControl extends BaseComponent {

	final ShotAssistant assistant;
	final double rectSize = config.getDouble("rectSize");
	final TemplateMatcher matcher = new TemplateMatcher(getConfig("matcher"));
	final PositionLimits limits = new PositionLimits(getConfig("limits"));
	final IntParam minValue = new IntParam(limits.minValue + ":Совпадение:0-100");

	final Rect roi;
	final Rectangle pos;
	final Mat template = new Mat();
	double templateLimit;

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
		templateLimit = Doubles.min(r1.value, r2.value);
	}

	MatchResult findMatch(Mat mat) {
		Mat sm = assistant.templateBuilder.build(mat);
		return matcher.findBest(sm, template);
	}

	boolean check(Mat mat) {
		if (template.empty()) return false;
		MatchResult mr = findMatch(mat);
		assistant.statusField.setText("Совпадение: " + mr.value); //TODO refactor
		double mv = Double.min(minValue.v * 0.01, templateLimit);
		return mr.value > mv && pos.contains(mr.point);
	}

}
