package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.TermCriteria;

import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.buildMat;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.show;
import static org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.RED;
import static org.bytedeco.javacpp.opencv_core.TermCriteria.MAX_ITER;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_video.meanShift;

public class JavaCvSandbox {

	public static void main(String[] args) {
		Mat m1 = imread("data/baboon1.jpg");
		Rect roi = new Rect(110, 260, 35, 40);
		rectangle(m1, roi, RED);
		show(m1, "input");
		Mat m2 = m1.apply(roi);

		Mat h2 = new HistogramBuilder().buildHue(m2, 65);
		Mat m3 = imread("data/baboon3.jpg");
		Mat cm3 = buildMat(r -> cvtColor(m3, r, COLOR_BGR2HSV));

		BackProjectHelper bph = new BackProjectHelper().setChannels(0).setRange(0, 180);
		Mat r1 = bph.calculate(cm3, h2);

		int mr1 = meanShift(r1, roi, new TermCriteria(MAX_ITER, 10, 0.01));
		System.out.println("meanShift = " + mr1);
		rectangle(m3, roi, RED);
		show(m3, "result2");
	}

}
