package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import com.typesafe.config.Config;

class PositionLimits extends BaseComponent {

	final double minValue = config.getDouble("minValue");
	final double offset = config.getDouble("offset");
	final double scale = config.getDouble("scale");
	final double rotation = config.getDouble("rotation");

	public PositionLimits(Config config) {
		super(config);
	}
}
