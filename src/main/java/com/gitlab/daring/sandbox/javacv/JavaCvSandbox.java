package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.loadAndShow;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.show;
import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgproc.THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.threshold;

public class JavaCvSandbox {

	public static void main(String[] args) {
		Mat m1 = loadAndShow("data/group.jpg", IMREAD_GRAYSCALE, null);
		show(new HistogramBuilder().buildImage(m1), "histogram");
		Mat m2 = new Mat();
		threshold(m1, m2, 60, 255, THRESH_BINARY);
		show(m2, "threshold");
	}

}
