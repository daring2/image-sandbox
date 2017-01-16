package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.buildHistogram;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.buildMat;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.show;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.bytedeco.javacpp.opencv_imgproc.threshold;

public class JavaCvSandbox {

	public static void main(String[] args) {
		Mat m1 = imread("data/waves.jpg");
		Rect roi = new Rect(0,0,165,75);
		rectangle(m1, roi, Scalar.BLACK);
		show(m1, "input");
		Mat m2 = m1.apply(roi);
//		show(new HistogramBuilder().buildGrayImage(m2), "reference histogram");
		BackProjectHelper bph = new BackProjectHelper();
		Mat h2 = new HistogramBuilder().setSize(32).setColored(true).build(m2);
		Mat r1 = bph.calculate(m1, h2);
		show(r1, "result1");
		Mat r2 = buildMat(r -> threshold(r1, r, 13, 255, THRESH_BINARY));
		show(r2, "result2");
	}

}
