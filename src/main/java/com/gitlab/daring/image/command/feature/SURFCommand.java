package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_xfeatures2d.SURF;

public class SURFCommand extends BaseCommand {

	final IntParam hessianThreshold = intParam(0, "20-200");
	final IntParam nOctaves = intParam(1, "0-10");
	final IntParam nOctaveLayers = intParam(2, "0-10");

	final SURF detector = SURF.create();

	public SURFCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		detector.setHessianThreshold(hessianThreshold.v);
		detector.setNOctaves(nOctaves.v);
		detector.setNOctaveLayers(nOctaveLayers.v);
		detector.detect(env.mat, env.keyPoints);
	}

}
