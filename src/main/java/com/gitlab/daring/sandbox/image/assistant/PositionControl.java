package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import com.gitlab.daring.sandbox.image.template.MatchResult;
import com.gitlab.daring.sandbox.image.template.TemplateMatcher;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;
import static com.gitlab.daring.sandbox.image.util.GeometryUtils.getCenterRect;

@NotThreadSafe
class PositionControl extends BaseComponent {

	final ShotAssistant assistant;
	final double rectSize = config.getDouble("rectSize");
	final TemplateMatcher matcher = new TemplateMatcher(getConfig("matcher"));
	final PositionLimits limits = new PositionLimits(getConfig("limits"));

	final Mat template = new Mat();
	final Rect roi;
	final Rectangle pos;

	PositionControl(ShotAssistant a) {
		super(a.config.getConfig("position"));
		assistant = a;
		roi = getCenterRect(a.getSize(), rectSize);
		pos = limits.buildRect(roi.x(), roi.y());
	}

	void setTemplate(Mat m) {
		new Mat(m, roi).copyTo(template);
	}

	boolean check(Mat mat) {
		Mat sm = assistant.templateBuilder.build(mat);
		MatchResult mr = matcher.findBest(sm, template);
		assistant.statusField.setText("result: " + mr.value); //TODO refactor
		return mr.value > limits.minValue && pos.contains(mr.point);
	}

}
