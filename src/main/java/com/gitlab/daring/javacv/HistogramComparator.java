package com.gitlab.daring.javacv;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.io.File;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.CV_COMP_INTERSECT;
import static org.bytedeco.javacpp.opencv_imgproc.compareHist;

class HistogramComparator {

	final Mat refMat;
	final HistogramBuilder hb;
	final Mat refHist;

	HistogramComparator(Mat refMat, int histSize) {
		this.refMat = refMat;
		this.hb = new HistogramBuilder()
			.setSize(histSize)
			.setColored(refMat.channels() > 1);
		this.refHist = hb.build(refMat);
	}

	double compare(Mat m, int method) {
		return compareHist(refHist, hb.build(m), method);
	}

	public static void main(String[] args) {
		Mat rm = imread("data/waves.jpg");
		HistogramComparator c = new HistogramComparator(rm, 8);
		for (String f : new File("data").list()) {
			Mat m = imread("data/" + f);
			double cr = c.compare(m, CV_COMP_INTERSECT) / m.rows() / m.cols();
			System.out.println(f + " = " + cr);
		}
	}

}
