package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.DMatchVector;
import org.bytedeco.javacpp.opencv_core.KeyPointVector;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_features2d.BFMatcher;
import org.bytedeco.javacpp.opencv_xfeatures2d.SURF;

import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.buildMat;
import static com.gitlab.daring.sandbox.javacv.JavaCvUtils.show;
import static com.gitlab.daring.sandbox.javacv.MatchUtils.selectBest;
import static org.bytedeco.javacpp.opencv_features2d.drawMatches;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;

public class JavaCvSandbox {

	public static void main(String[] args) {
		MatchResult r1 = calcSurf(imread("data/church01.jpg"));
		MatchResult r2 = calcSurf(imread("data/church02.jpg"));
		BFMatcher m = new BFMatcher();
		DMatchVector ms = new DMatchVector();
		m.match(r1.features, r2.features, ms);
		show(buildMat(r -> drawMatches(
			r1.image, r1.keyPoints,
			r2.image, r2.keyPoints,
			selectBest(ms, 25), r
		)), "SURF Feature Matches");
	}

	static MatchResult calcSurf(Mat m) {
		SURF d = SURF.create(2500, 4, 2, false, false);
		KeyPointVector kps = new KeyPointVector();
		d.detect(m, kps);
		Mat fs = buildMat(r -> d.compute(m, kps, r));
		return new MatchResult(m, kps, fs);
	}

}
