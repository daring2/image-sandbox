package com.gitlab.daring.sandbox.image.util;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import java.util.function.Consumer;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ImageUtils {

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

	private ImageUtils() {
	}

}
