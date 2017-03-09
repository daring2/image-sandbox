package com.gitlab.daring.image.util;

import com.gitlab.daring.image.component.BaseCanvasFrame;
import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.javacpp.opencv_core.*;

import java.awt.Point;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;
import static com.google.common.collect.Iterables.toArray;
import static java.lang.Math.min;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class ImageUtils {

    public static Mat loadAndShow(String file) {
        Mat m = imread(file);
        showMat(m, file);
        return m;
    }

    public static Mat buildMat(Consumer<Mat> func) {
        Mat m = new Mat();
        func.accept(m);
        return m;
    }

    public static void showMat(Mat m, String title) {
        BaseCanvasFrame frame = new BaseCanvasFrame(title);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.showMat(m);
    }

    public static Mat convertToGrey(Mat m) {
        if (m.channels() == 1) return m;
        return buildMat(r -> cvtColor(m, r, COLOR_BGR2GRAY));
    }

    public static void flipMat(Mat mat, int code) {
        flip(mat.clone(), mat, code);
    }

    public static Mat resizeMat(Mat m, double scale) {
        return buildMat(r -> resize(m, r, new Size(), scale, scale, INTER_LINEAR));
    }

    public static Mat cropMat(Mat m, Rectangle r) {
        return m.apply(toOpencv(r));
    }

    public static Mat[] cropToMin(Mat m1, Mat m2) {
        Rectangle r = new Rectangle(min(m1.cols(), m2.cols()), min(m1.rows(), m2.rows()));
        return new Mat[]{cropMat(m1, r), cropMat(m2, r)};
    }

    public static Mat rotateMat(Mat m, double angle) {
        Point2f cp = new Point2f(m.cols() / 2.0f, m.rows() / 2.0f);
        Mat rm = getRotationMatrix2D(cp, angle, 1);
        return buildMat(r -> warpAffine(m, r, rm, m.size()));
    }

    public static void addWeightedMat(Mat m1, Mat m2, Mat dm, double f) {
        if (f > 0) addWeighted(m1, 1 - f, m2, f, 0, dm);
    }

    public static void drawRect(Mat m, Rectangle r, Scalar c, int th) {
        rectangle(m, toOpencv(r), c, th, LINE_8, 0);
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
