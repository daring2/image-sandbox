package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

public class JavaCvSandbox {

	public static void main(String[] args) {
		opencv_core.Mat image = imread("data/boldt.jpg", IMREAD_GRAYSCALE);
		CanvasFrame frame = new CanvasFrame("My Image", 1);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.showImage(new OpenCVFrameConverter.ToMat().convert(image));
	}

}
