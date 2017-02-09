package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_features2d.GFTTDetector;

public class GFTTCommand extends BaseCommand {

	final IntParam maxFeatures = intParam(0, 100, "0-100");
	final DoubleParam qualityLevel = doubleParam(1, 1, "0-100");
	final DoubleParam minDistance = doubleParam(2, 1, "0-20");
	final IntParam blockSize = intParam(3, 3, "0-20");
	final GFTTDetector detector = GFTTDetector.create();

	public GFTTCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		detector.setMaxFeatures(maxFeatures.v);
		detector.setQualityLevel(qualityLevel.v * 0.01);
		detector.setMinDistance(minDistance.v);
		detector.setBlockSize(blockSize.v);
		detector.detect(env.mat, env.keyPoints);
	}

}
