package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_xfeatures2d.SIFT;

class SIFTCommand extends BaseCommand {

	final IntParam maxFeatures = intParam(0, 100,"0-100");
	final IntParam nOctaveLayers = intParam(1, 3,"0-10");
	final DoubleParam contrastThreshold = doubleParam(2, 4, "0-100");
	final DoubleParam edgeThreshold = doubleParam(3, 10,"0-100");
	final DoubleParam sigma = doubleParam(4, 1.6,"0.1-10");

	SIFTCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		SIFT detector = SIFT.create(maxFeatures.v, nOctaveLayers.v, contrastThreshold.v * 0.01, edgeThreshold.v, sigma.v);
		detector.detect(env.mat, env.keyPoints);
	}

}