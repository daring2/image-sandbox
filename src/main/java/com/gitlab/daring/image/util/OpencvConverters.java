package com.gitlab.daring.image.util;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Size;
import java.awt.*;
import java.util.Collection;
import static com.google.common.collect.Iterables.toArray;

public class OpencvConverters {

	public static Point toJava(opencv_core.Point p) {
		return new Point(p.x(), p.y());
	}

	public static Dimension toJava(Size size) {
		return new Dimension(size.width(), size.height());
	}

	public static Rectangle toJava(Rect rect) {
		return new Rectangle(rect.x(), rect.y(), rect.width(), rect.height());
	}

	public static Size toOpencv(Dimension d) {
		return new Size(d.width, d.height);
	}

	public static Rect toOpencv(Rectangle r) {
		return new Rect(r.x, r.y, r.width, r.height);
	}

	public static MatVector toOpencv(Collection<Mat> ms) {
		return new MatVector(toArray(ms, Mat.class));
	}

	private OpencvConverters() {
	}

}
