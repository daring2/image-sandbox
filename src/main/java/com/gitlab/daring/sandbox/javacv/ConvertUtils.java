package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.javacpp.indexer.FloatRawIndexer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point2f;
import org.bytedeco.javacpp.opencv_core.Point2fVector;

import static org.bytedeco.javacpp.opencv_core.CV_32FC2;

class ConvertUtils {

	static float[] imageToFloatArray(Mat m) {
		FloatRawIndexer ind = m.createIndexer();
		float[] r = new float[m.rows()];
		for (int i = 0; i < m.rows(); i++) r[i] = ind.get(i);
		return r;
	}

	static Mat toMat(Point2fVector v) {
		Mat m = new Mat(1, (int) v.size(), CV_32FC2);
		FloatIndexer ind = m.createIndexer();
		for (int i = 0; i < v.size(); i++) {
			Point2f p = v.get(i);
			ind.put(0, i, 0, p.x());
			ind.put(0, i, 1, p.y());
		}
		return m;
	}

	private ConvertUtils() {
	}

}
