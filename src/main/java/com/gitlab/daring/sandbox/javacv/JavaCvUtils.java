package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.util.Collection;
import java.util.function.Consumer;

import static com.google.common.base.MoreObjects.firstNonNull;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static org.bytedeco.javacpp.opencv_core.minMaxLoc;
import static org.bytedeco.javacpp.opencv_core.normalize;
import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_COLOR;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.equalizeHist;

class JavaCvUtils {

	static Mat loadAndShow(String file, int flags, String title) {
		Mat m = imread(file, flags);
		show(m, firstNonNull(title, file));
		return m;
	}

	static Mat loadAndShow(String file) {
		return loadAndShow(file, IMREAD_COLOR, null);
	}

	static void show(Mat m, String title) {
		CanvasFrame frame = new CanvasFrame(title, 1);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.showImage(new OpenCVFrameConverter.ToMat().convert(m));
	}

	static Mat buildMat(Consumer<Mat> func) {
		Mat r = new Mat();
		func.accept(r);
		return r;
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
