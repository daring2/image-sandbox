package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import com.gitlab.daring.sandbox.image.template.MatchResult;
import com.gitlab.daring.sandbox.image.template.TemplateMatcher;
import org.bytedeco.javacpp.opencv_core.Mat;
import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;
import static com.gitlab.daring.sandbox.image.util.ConvertUtils.toJava;
import static com.gitlab.daring.sandbox.image.util.ConvertUtils.toOpencv;
import static com.gitlab.daring.sandbox.image.util.GeometryUtils.getCenterRect;

@NotThreadSafe
class PositionControl extends BaseComponent {

	final ShotAssistant assistant;
	final double rectSize = config.getDouble("rectSize");
	final TemplateMatcher matcher = new TemplateMatcher(getConfig("matcher"));
	final PositionLimits limits = new PositionLimits(getConfig("limits"));

	final Mat template = new Mat();
	Rectangle pos;

	PositionControl(ShotAssistant a) {
		super(a.config.getConfig("position"));
		this.assistant = a;
	}

	void setSimple(Mat m) {
		Rectangle rect = getCenterRect(toJava(m.size()), rectSize);
		pos = limits.buildRect(rect.getLocation());
		new Mat(m, toOpencv(rect)).copyTo(template);
	}

	boolean check(Mat mat) {
		Mat sm = assistant.sampleBuilder.build(mat);
		MatchResult mr = matcher.findBest(sm, template);
		assistant.statusField.setText("result: " + mr.value); //TODO refactor
		return mr.value > limits.minValue && pos.contains(mr.point);
	}

}
