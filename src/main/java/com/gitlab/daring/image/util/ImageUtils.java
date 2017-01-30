package com.gitlab.daring.image.util;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point2f;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import java.util.function.Consumer;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static org.bytedeco.javacpp.opencv_core.addWeighted;
import static org.bytedeco.javacpp.opencv_core.flip;
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
		CanvasFrame frame = new CanvasFrame(title, 1);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.showImage(new ToMat().convert(m));
	}

	public static void flipMat(Mat mat, int code) {
		buildMat(m -> flip(mat, m, code)).copyTo(mat);
	}

	public static Mat resizeMat(Mat m, double scale) {
		return buildMat(r -> resize(m, r, new Size(), scale, scale, INTER_LINEAR));
	}

	public static Mat rotateMat(Mat m, double angle) {
		Point2f cp = new Point2f(m.cols() / 2.0f, m.rows() / 2.0f);
		Mat rm = getRotationMatrix2D(cp, angle, 1);
		return buildMat(r -> warpAffine(m, r, rm, m.size()));
	}

	public static void addWeightedMat(Mat m1, Mat m2, Mat dm, double f) {
		if (f > 0) addWeighted(m1, 1 - f, m2, f, 0, dm);
	}

	private ImageUtils() {
	}

}