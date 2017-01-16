package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.indexer.FloatRawIndexer;
import org.bytedeco.javacpp.opencv_core.Mat;

class ConvertUtils {

	static float[] imageToFloatArray(Mat m) {
		FloatRawIndexer ind = m.createIndexer();
		float[] r = new float[m.rows()];
		for (int i = 0; i < m.rows(); i++) r[i] = ind.get(i);
		return r;
	}

	private ConvertUtils() {
	}
}
