package com.gitlab.daring.image.util;

import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Point2f;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;
import static com.google.common.collect.Iterables.toArray;
import static org.bytedeco.javacpp.opencv_core.CV_32F;

public class ImageUtils {

    public static Mat buildMat(Consumer<Mat> func) {
        Mat m = new Mat();
        func.accept(m);
        return m;
    }

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

    public static Mat newFloatMat(float[][] vs) {
        Mat m = new Mat(vs.length, vs[0].length, CV_32F);
        FloatIndexer ind = m.createIndexer();
        for (int y = 0; y < vs.length; y++) ind.put(y, vs[y]);
        return m;
    }

    public static MatVector newMatVector(Collection<Mat> ms) {
        return new MatVector(toArray(ms, Mat.class));
    }

    public static Point2f newPointArray(List<Point> ps) {
        Point2f rp = new Point2f(ps.size());
        for (int i = 0; i < ps.size(); i++) {
            Point p = ps.get(i);
            rp.position(i).x(p.x).y(p.y);
        }
        return rp.position(0);
    }

    private ImageUtils() {
    }

}
