package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.indexer.FloatRawIndexer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Scalar;

import static org.bytedeco.javacpp.opencv_imgproc.*;

public class HoughCirclesCommand extends BaseCommand {

	final IntParam dp = intParam(1, "0-10");
	final IntParam minDist = intParam(100, "0-1000");
	final IntParam p1 = intParam(200, "0-1000");
	final IntParam p2 = intParam(100, "0-1000");
	final IntParam minRadius = intParam(0, "0-1000");
	final IntParam maxRadius = intParam(0, "0-1000");
	final IntParam limit = intParam(Integer.MAX_VALUE, "0-100");

	final int method = CV_HOUGH_GRADIENT;

	public HoughCirclesCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat cs = new Mat();
		HoughCircles(env.mat, cs, method, dp.v, minDist.v, p1.v, p2.v, minRadius.v, maxRadius.v);
		if (cs.empty()) return;
		FloatRawIndexer ind = cs.createIndexer();
		long size = Math.min(ind.cols(), limit.v);
		for (int i = 0; i < size; i++) {
			Point cp = new Point((int) ind.get(0, i, 0), (int) ind.get(0, i, 1));
			int radius = (int) ind.get(0, i, 2);
			circle(env.mat, cp, radius, Scalar.WHITE);
		}
	}

}