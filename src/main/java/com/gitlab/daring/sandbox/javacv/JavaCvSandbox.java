package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import static com.google.common.base.MoreObjects.firstNonNull;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

public class JavaCvSandbox {

	public static void main(String[] args) {
		loadAndShow("data/boldt.jpg", null);
		loadAndShow("data/rain.jpg", null);
	}

	private static Mat loadAndShow(String file, String title) {
		Mat m = imread(file);// IMREAD_GRAYSCALE
		show(m, firstNonNull(title, file));
		return m;
	}

	private static void show(Mat m, String title) {
		CanvasFrame frame = new CanvasFrame(title, 1);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.showImage(new OpenCVFrameConverter.ToMat().convert(m));
	}

}
