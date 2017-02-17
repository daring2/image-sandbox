package com.gitlab.daring.image.template;

import java.awt.*;

public class MatchResult {

	public final Rectangle rect;
	public final Point point;
	public final double value;

	public MatchResult(Rectangle rect, double value) {
		this.rect = rect;
		this.point = rect.getLocation();
		this.value = value;
	}

}
