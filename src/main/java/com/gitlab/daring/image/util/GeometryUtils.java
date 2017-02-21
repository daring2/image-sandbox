package com.gitlab.daring.image.util;

import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Size;

import java.awt.*;

import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;

public class GeometryUtils {

	public static Rectangle getCenterRect(Rectangle r, double rectSize) {
		Point p = getCenter(r);
		Dimension sd = scale(r.getSize(), rectSize);
		return new Rectangle(p.x - sd.width / 2, p.y - sd.height / 2, sd.width, sd.height);
	}

	public static Rectangle getCenterRect(Dimension d, double rectSize) {
		return getCenterRect(new Rectangle(0, 0, d.width, d.height), rectSize);
	}

	public static Rect getCenterRect(Size size, double rectSize) {
		return toOpencv(getCenterRect(toJava(size), rectSize));
	}

	public static Point getCenter(Rectangle r) {
		return new Point(r.x + r.width / 2, r.y + r.height / 2);
	}

	public static Point getCenter(Dimension d) {
		return new Point(d.width / 2, d.height / 2);
	}

	public static Dimension scale(Dimension d, double f) {
		return new Dimension(roundInt(d.width * f), roundInt(d.height * f));
	}

	public static Dimension scaleToMax(Dimension d, Dimension md) {
		if (md.width >= d.width && md.height >= d.height) return d;
		double f = Math.min(1.0 * md.width / d.width, 1.0 * md.height / d.height);
		return scale(d, f);
	}

	public static int roundInt(double v) {
		return (int) Math.round(v);
	}

	private GeometryUtils() {
	}

}
