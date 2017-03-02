package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_xfeatures2d.SIFT;

class SIFTCommand extends BaseCommand {

	final IntParam maxFeatures = intParam(100,"0-100");
	final IntParam nOctaveLayers = intParam(3,"0-10");
	final DoubleParam contrastThreshold = doubleParam(4, "0-100");
	final DoubleParam edgeThreshold = doubleParam(10,"0-100");
	final DoubleParam sigma = doubleParam(1.6,"0.1-10");

	SIFTCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		SIFT detector = SIFT.create(maxFeatures.v, nOctaveLayers.v, contrastThreshold.pv(), edgeThreshold.v, sigma.v);
		detector.detect(env.mat, env.keyPoints);
	}

}
