package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import static org.bytedeco.javacpp.opencv_imgproc.calcHist;

class HistogramBuilder {

	Mat build(Mat m) {
		MatVector mv = new MatVector(1).put(0, m);
		Mat h = new Mat();
		calcHist(mv, new int[] {0}, new Mat(), h, new int[] {256}, new float[] {0, 255});
		return h;
	}

}
