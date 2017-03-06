package com.gitlab.daring.image.gmv.assistant;

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
import static java.lang.String.format;

@NotThreadSafe
class PositionControl extends BaseComponent {

	final ShotAssistant assistant;
	final IntParam rectSize = new IntParam("0:Размер объекта:0-100").bind(config, "rectSize");
	final TemplateMatcher matcher = new TemplateMatcher(getConfig("matcher"));
	final PositionLimits limits = new PositionLimits(getConfig("limits"));
	final IntParam minValue = new IntParam("0:Совпадение:0-100").bind(limits.config, "minValue");

	final Mat template = new Mat();
	Rect roi;
	Rectangle pos;
	double templateLimit;

	PositionControl(ShotAssistant a) {
		super(a.config.getConfig("position"));
		assistant = a;
		rectSize.changeEvent.onFire(this::updateRectSize);
		updateRectSize();
	}

	void updateRectSize() {
		roi = getCenterRect(assistant.getSize(), rectSize.pv());
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
		double mv = Double.min(minValue.pv(), templateLimit);
		String statusText = format("Совпадение: %.3f (лимит %.3f)", mr.value, mv);
		assistant.statusField.setText(statusText); //TODO refactor
		return mr.value > mv && pos.contains(mr.point);
	}

}
