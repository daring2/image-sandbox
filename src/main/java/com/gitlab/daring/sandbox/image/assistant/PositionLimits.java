package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import com.typesafe.config.Config;
import java.awt.*;

class PositionLimits extends BaseComponent {

	final int offset = config.getInt("offset");
	final double scale = config.getDouble("scale");
	final double rotation = config.getDouble("rotation");
	final double minValue = config.getDouble("minValue");

	PositionLimits(Config config) {
		super(config);
	}

	Rectangle buildRect(Point p) {
		return new Rectangle(p.x - offset, p.y - offset, p.x + offset, p.y + offset);
	}

}
