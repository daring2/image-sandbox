package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import static com.google.common.base.MoreObjects.firstNonNull;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_COLOR;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

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

	private JavaCvUtils() {
	}

}
