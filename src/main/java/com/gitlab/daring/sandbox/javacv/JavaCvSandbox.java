package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.bytedeco.javacpp.opencv_imgproc.threshold;

public class JavaCvSandbox {

	public static void main(String[] args) {
		Mat m1 = imread("data/waves.jpg", IMREAD_GRAYSCALE);
		Rect roi = new Rect(360,55,40,50);
		rectangle(m1, roi, Scalar.BLACK);
		show(m1, "input");
		Mat m2 = m1.apply(roi);
		show(new HistogramBuilder().buildImage(m2), "reference histogram");
		BackProjectHelper bph = new BackProjectHelper();
		Mat r1 = bph.calculate(m1, buildHistogram(m2));
		show(r1, "result1");
		Mat r2 = buildMat(r -> threshold(r1, r, 30, 255, THRESH_BINARY));
		show(r2, "result2");
	}

}
