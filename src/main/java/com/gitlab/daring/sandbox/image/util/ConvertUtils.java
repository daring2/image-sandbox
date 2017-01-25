package com.gitlab.daring.sandbox.image.util;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Rect;
import java.awt.*;

public class ConvertUtils {

	public static Point toJava(opencv_core.Point p) {
		return new Point(p.x(), p.y());
	}

	public static Dimension toJava(opencv_core.Size size) {
		return new Dimension(size.width(), size.height());
	}

	public static Rect toOpencv(Rectangle r) {
		return new Rect(r.x, r.y, r.width, r.height);
	}

	private ConvertUtils() {
	}

}
