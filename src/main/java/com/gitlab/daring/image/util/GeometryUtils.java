package com.gitlab.daring.image.util;

import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Size;

import java.awt.*;

import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;

public class GeometryUtils {

	public static Rectangle getCenterRect(Dimension d, double rectSize) {
		Point p = getCenter(d);
		Dimension sd = scale(d, rectSize);
		return new Rectangle(p.x - sd.width / 2, p.y - sd.height / 2, sd.width, sd.height);
	}

	public static Rect getCenterRect(Size size, double rectSize) {
		return toOpencv(getCenterRect(toJava(size), rectSize));
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
