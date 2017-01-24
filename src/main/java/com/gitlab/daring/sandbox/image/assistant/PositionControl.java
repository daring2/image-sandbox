package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;

class PositionControl extends BaseComponent {

	final PositionLimits limits = new PositionLimits(getConfig("limits"));

	public PositionControl(ShotAssistant a) {
		super(a.config.getConfig("position"));
	}

	void setSimple(Mat m) {
		//TODO implement
	}

}
