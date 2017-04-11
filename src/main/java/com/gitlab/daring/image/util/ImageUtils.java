package com.gitlab.daring.image.util;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;

import java.awt.*;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;

public class ImageUtils {

    public static Rectangle centerRect(Mat m, double rectSize) {
        return getCenterRect(toJava(m.size()), rectSize);
    }

    public static Mat cropMat(Mat m, Rectangle r) {
        return m.apply(toOpencv(r));
    }

    public static Mat cropCenter(Mat m, double rectSize) {
        if (rectSize == 1.0) return m;
        return cropMat(m, centerRect(m, rectSize));
    }

    public static Mat smat(int v) {
        return new Mat(new byte[]{(byte) v});
    }

    public static Scalar newScalar(String color) {
        Color c = Color.decode(color);
        return new Scalar(c.getBlue(), c.getGreen(), c.getRed(), 0);
    }

    private ImageUtils() {
    }

}
