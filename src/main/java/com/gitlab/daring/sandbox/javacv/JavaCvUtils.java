package com.gitlab.daring.sandbox.javacv;

import com.gitlab.daring.sandbox.image.util.ImageUtils;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;

import java.util.Collection;
import java.util.function.Consumer;

import static com.gitlab.daring.sandbox.image.util.ImageUtils.showMat;
import static com.google.common.base.MoreObjects.firstNonNull;
import static org.bytedeco.javacpp.opencv_core.minMaxLoc;
import static org.bytedeco.javacpp.opencv_core.normalize;
import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_COLOR;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.equalizeHist;

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

	static Mat equalizeHistogram(Mat m) {
		return buildMat(r -> equalizeHist(m, r));
	}

	static Mat normalizeImage(Mat m) {
		return buildMat(r -> normalize(m, r));
	}

	static IntPointer intPointer(int... vs) {
		return new IntPointer(vs);
	}

	static double[] calcMinMax(Mat m) {
		DoublePointer min = new DoublePointer(1);
		DoublePointer max = new DoublePointer(1);
		minMaxLoc(m, min, max, null, null, new Mat());
		return new double[] {min.get(), max.get()};
	}

	static MatVector newMatVector(Collection<Mat> ms) {
		return new MatVector(ms.toArray(new Mat[] {}));
	}

	private JavaCvUtils() {
	}

}
