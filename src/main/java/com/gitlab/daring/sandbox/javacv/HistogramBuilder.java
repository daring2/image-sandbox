package com.gitlab.daring.sandbox.javacv;

import com.google.common.primitives.Floats;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Scalar;
import static com.gitlab.daring.sandbox.javacv.ConvertUtils.imageToFloatArray;
import static org.bytedeco.javacpp.opencv_core.CV_8U;
import static org.bytedeco.javacpp.opencv_imgproc.calcHist;
import static org.bytedeco.javacpp.opencv_imgproc.line;

class HistogramBuilder {

	int size = 256;

	Mat build(Mat m) {
		MatVector mv = new MatVector(1).put(0, m);
		Mat h = new Mat();
		calcHist(mv, new int[] {0}, new Mat(), h, new int[] {size}, new float[] {0, 255});
		return h;
	}

	Mat buildImage(Mat m) {
		Mat h = build(m);
		float[] hvs = imageToFloatArray(h);
		double scale = 0.9 * size / Floats.max(hvs);
		Mat r = new Mat(size, size, CV_8U, Scalar.WHITE);
		for (int i = 0; i < hvs.length; i++) {
			int v = (int) (hvs[i] * scale);
			line(r, new Point(i, size), new Point(i, size - v), Scalar.BLACK);
		}
		return r;
	}

}
