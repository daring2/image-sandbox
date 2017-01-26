package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import com.typesafe.config.Config;
import java.awt.*;

class PositionLimits extends BaseComponent {

	final int offset = config.getInt("offset");
	final double scale = config.getDouble("scale");
	final double angle = config.getDouble("angle");
	final double minValue = config.getDouble("minValue");

	PositionLimits(Config config) {
		super(config);
	}

	Rectangle buildPositionRect(int x, int y) {
		return new Rectangle(x - offset, y - offset, offset* 2, offset * 2);
	}

}
