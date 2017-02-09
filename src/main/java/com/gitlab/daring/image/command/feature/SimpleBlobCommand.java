package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_features2d.SimpleBlobDetector;

public class SimpleBlobCommand extends BaseCommand {

	//TODO support all params
	final IntParam minThreshold = intParam(0, 0, "0-255");
	final IntParam maxThreshold = intParam(1, 255, "0-255");
	final IntParam thresholdStep = intParam(2, 5, "0-50");
	final IntParam minDistBetweenBlobs = intParam(3, 10, "0-1000");
	final IntParam minArea = intParam(4, 10, "0-10000");
	final IntParam maxArea = intParam(5, 10000, "0-10000");

	public SimpleBlobCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		SimpleBlobDetector.Params ps = new SimpleBlobDetector.Params();
		ps.minThreshold(minThreshold.v);
		ps.maxThreshold(maxThreshold.v);
		ps.thresholdStep(thresholdStep.v);
		ps.minDistBetweenBlobs(minDistBetweenBlobs.v);
		ps.minArea(minArea.v);
		ps.maxArea(maxArea.v);
		ps.filterByArea(minArea.v < maxArea.v);
		ps.filterByColor(false);
		ps.filterByCircularity(false);
		ps.filterByConvexity(false);
		SimpleBlobDetector d = SimpleBlobDetector.create(ps);
		d.detect(env.mat, env.keyPoints);
	}

}
