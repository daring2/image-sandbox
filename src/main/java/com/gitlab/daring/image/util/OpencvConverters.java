package com.gitlab.daring.image.util;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Size;

import java.awt.*;
import java.util.List;

import static one.util.streamex.LongStreamEx.range;

public class OpencvConverters {

    public static Point toJava(opencv_core.Point p) {
        return new Point(p.x(), p.y());
    }

    public static opencv_core.Point toOpencv(Point p) {
        return new opencv_core.Point(p.x, p.y);
    }

    public static Dimension toJava(Size size) {
        return new Dimension(size.width(), size.height());
    }

    public static Size toOpencv(Dimension d) {
        return new Size(d.width, d.height);
    }

    public static Rectangle toJava(Rect rect) {
        return new Rectangle(rect.x(), rect.y(), rect.width(), rect.height());
    }

    public static Rectangle newRect(opencv_core.Point p, Size size) {
        return new Rectangle(toJava(p), toJava(size));
    }

    public static Rect toOpencv(Rectangle r) {
        return new Rect(r.x, r.y, r.width, r.height);
    }

    public static List<Mat> toJava(MatVector ms) {
        return range(ms.size()).mapToObj(ms::get).toList();
    }

    private OpencvConverters() {
    }

}
