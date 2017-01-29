package com.gitlab.daring.javacv;

import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core.Mat;

import static com.gitlab.daring.image.util.ImageUtils.buildMat;
import static org.bytedeco.javacpp.opencv_core.normalize;

class JavaCvUtils {

	static Mat normalizeImage(Mat m) {
		return buildMat(r -> normalize(m, r));
	}

	static IntPointer intPointer(int... vs) {
		return new IntPointer(vs);
	}

	private JavaCvUtils() {
	}

}