package com.gitlab.daring.javacv;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.opencv_core.Mat;

import static com.gitlab.daring.image.util.ImageUtils.buildMat;
import static com.gitlab.daring.javacv.JavaCvUtils.intPointer;
import static com.gitlab.daring.javacv.JavaCvUtils.normalizeImage;
import static org.bytedeco.javacpp.opencv_imgproc.calcBackProject;

class BackProjectHelper {

	private int[] channels = new int[] {0, 1, 2};
	private float[] range = new float[] {0, 255};

	Mat calculate(Mat m, Mat h) {
		return buildMat(r -> {
			IntPointer chs = intPointer(channels);
			PointerPointer<FloatPointer> rs = new PointerPointer<>(range, range, range);
			calcBackProject(m, 1, chs, normalizeImage(h), r, rs, 255, true);
		});
	}

	BackProjectHelper setChannels(int... channels) {
		this.channels = channels;
		return this;
	}

	BackProjectHelper setRange(float... range) {
		this.range = range;
		return this;
	}

}
