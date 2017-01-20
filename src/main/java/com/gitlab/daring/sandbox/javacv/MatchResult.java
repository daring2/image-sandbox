package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.KeyPointVector;
import org.bytedeco.javacpp.opencv_core.Mat;

class MatchResult {

	final Mat image;
	final KeyPointVector keyPoints;
	final Mat features;

	MatchResult(Mat image, KeyPointVector keyPoints, Mat features) {
		this.image = image;
		this.keyPoints = keyPoints;
		this.features = features;
	}

}
