package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.opencv_core.Mat;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.buildMat;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.normalizeImage;
import static org.bytedeco.javacpp.opencv_imgproc.calcBackProject;

class BackProjectHelper {

	Mat calculate(Mat m, Mat h) {
		return buildMat(r -> {
			IntPointer chs = new IntPointer(0, 1, 2);
			float[] hr = {0, 255};
			PointerPointer<FloatPointer> hrs = new PointerPointer<>(hr, hr, hr);
			calcBackProject(m, 1, chs, normalizeImage(h), r, hrs, 255, true);
		});
	}

}
