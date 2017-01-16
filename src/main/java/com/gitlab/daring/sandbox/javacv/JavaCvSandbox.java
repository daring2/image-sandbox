package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.Mat;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.loadAndShow;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.show;
import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_GRAYSCALE;

public class JavaCvSandbox {

	public static void main(String[] args) {
		Mat m1 = loadAndShow("data/group.jpg", IMREAD_GRAYSCALE, null);
		Mat h1 = new HistogramBuilder().buildImage(m1);
		show(h1, "histogram");
	}

}
