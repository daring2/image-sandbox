package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Scalar;

import java.awt.*;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;
import static org.bytedeco.javacpp.opencv_core.LINE_8;
import static org.bytedeco.javacpp.opencv_imgproc.circle;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

public enum MarkerType {

    Rectangle, Circle;

    public void drawCenter(Mat m, IntParam p, int color, int th) {
        Dimension d = toJava(m.size());
        Rectangle cr = getCenterRect(d, p.getPv());
        Scalar c = Scalar.all(color);
        if (this == Rectangle) {
            rectangle(m, toOpencv(cr), c, th, LINE_8, 0);
        } else if (this == Circle) {
            Point cp = new Point(d.width / 2, d.height / 2);
            int radius = Math.min(cr.width, cr.height) / 2;
            circle(m, cp, radius, c, th, LINE_8, 0);
        } else {
            throw new IllegalArgumentException("markerType=" + this);
        }
    }
}