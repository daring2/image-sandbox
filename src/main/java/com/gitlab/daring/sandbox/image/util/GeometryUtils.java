package com.gitlab.daring.sandbox.image.util;

import java.awt.*;

public class GeometryUtils {

	public static Rectangle getCenterRect(Dimension d, double rectSize) {
		return newCenterRect(getCenter(d), scale(d, rectSize));
	}

	public static Rectangle newCenterRect(Point p, Dimension d) {
		return new Rectangle(p.x - d.width, p.y - d.height, p.x + d.width, p.y + d.height);
	}

	public static Point getCenter(Dimension d) {
		return new Point(d.width / 2, d.height / 2);
	}

	public static Dimension scale(Dimension d, double f) {
		return new Dimension(roundInt(d.width * f), roundInt(d.height * f));
	}

	public static int roundInt(double v) {
		return (int) Math.round(v);
	}

	private GeometryUtils() {
	}

}
