package com.gitlab.daring.image.command.structure;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.awt.*;
import java.util.Comparator;

import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.Comparator.comparingDouble;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public enum ContourMetric {

    Length, Area, Size, MinSize, Diameter;

    Comparator<Contour> comparator = comparingDouble(c -> c.getMetric(this));

    public double calculate(Mat m) {
        if (this == Length) {
            return arcLength(m, false);
        } else if (this == Area) {
            return contourArea(m);
        } else if (this == Size) {
            Rectangle r = toJava(boundingRect(m));
            return Math.max(r.width, r.height);
        } else if (this == MinSize) {
            Rectangle r = toJava(boundingRect(m));
            return Math.min(r.width, r.height);
        } else if (this == Diameter) {
            Rectangle r = toJava(boundingRect(m));
            return sqrt(pow(r.width, 2) + pow(r.height, 2));
        } else {
            throw new IllegalArgumentException("metric=" + m);
        }
    }

}