package com.gitlab.daring.sandbox.image.util;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Size;
import java.awt.*;

public class OpencvConverters {

	public static Point toJava(opencv_core.Point p) {
		return new Point(p.x(), p.y());
	}

	public static Dimension toJava(Size size) {
		return new Dimension(size.width(), size.height());
	}

	public static Size toOpencv(Dimension d) {
		return new Size(d.width, d.height);
	}

	public static Rect toOpencv(Rectangle r) {
		return new Rect(r.x, r.y, r.width, r.height);
	}

	private OpencvConverters() {
	}

}
