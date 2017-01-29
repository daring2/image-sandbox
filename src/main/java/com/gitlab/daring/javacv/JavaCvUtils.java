package com.gitlab.daring.javacv;

import com.gitlab.daring.image.util.ImageUtils;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.function.Consumer;

import static com.gitlab.daring.image.util.ImageUtils.showMat;
import static com.google.common.base.MoreObjects.firstNonNull;
import static org.bytedeco.javacpp.opencv_core.normalize;
import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_COLOR;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

class JavaCvUtils {

	static Mat loadAndShow(String file, int flags, String title) {
		Mat m = imread(file, flags);
		showMat(m, firstNonNull(title, file));
		return m;
	}

	static Mat loadAndShow(String file) {
		return loadAndShow(file, IMREAD_COLOR, null);
	}

	static Mat buildMat(Consumer<Mat> func) {
		return ImageUtils.buildMat(func);
	}

	static Mat normalizeImage(Mat m) {
		return buildMat(r -> normalize(m, r));
	}

	static IntPointer intPointer(int... vs) {
		return new IntPointer(vs);
	}

	private JavaCvUtils() {
	}

}
