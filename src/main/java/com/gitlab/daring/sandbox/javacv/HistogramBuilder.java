package com.gitlab.daring.sandbox.javacv;

import com.google.common.primitives.Floats;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Scalar;

import static com.gitlab.daring.sandbox.javacv.ConvertUtils.imageToFloatArray;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.buildMat;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.intPointer;
import static org.bytedeco.javacpp.opencv_core.CV_8U;
import static org.bytedeco.javacpp.opencv_core.split;
import static org.bytedeco.javacpp.opencv_imgproc.*;

class HistogramBuilder {

	private int size = 256;
	private boolean colored = false;

	Mat build(Mat m) {
		IntPointer chs = colored ? intPointer(0, 1, 2) : intPointer(0);
		IntPointer sizes = colored ? intPointer(size, size, size) : intPointer(size);
		float[] hr = new float[] {0, 255};
		PointerPointer<FloatPointer> hrs = colored ? new PointerPointer<>(hr, hr, hr) : new PointerPointer<>(hr);
		return buildMat(h -> {
			calcHist(m, 1, chs, new Mat(), h, colored ? 3: 1, sizes, hrs, true, false);
		});
	}

	Mat buildGrayImage(Mat m) {
		if (colored) throw new IllegalArgumentException("colored=true");
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

	Mat buildHue(Mat m, int minSat) {
		Mat cm = buildMat(r -> cvtColor(m, r, COLOR_BGR2HSV));
		Mat sm = new Mat();
		MatVector chs = new MatVector();
		split(cm, chs);
		threshold(chs.get(1), sm, minSat, 255, THRESH_BINARY);
		FloatPointer hrs = new FloatPointer(0, 180);
		return buildMat(r -> {
			calcHist(cm, 1, intPointer(0), sm, r, 1, intPointer(size), hrs);
		});
	}

	HistogramBuilder setSize(int size) {
		this.size = size;
		return this;
	}

	HistogramBuilder setColored(boolean colored) {
		this.colored = colored;
		return this;
	}
}
