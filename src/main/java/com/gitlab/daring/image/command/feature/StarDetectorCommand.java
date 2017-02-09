package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_xfeatures2d.StarDetector;

class StarDetectorCommand extends BaseCommand {

	final IntParam maxSize = intParam(0, 45, "0-100");
	final IntParam th1 = intParam(1, 30, "0-100");
	final IntParam th2 = intParam(2, 10, "0-100");
	final IntParam th3 = intParam(3, 8, "0-100");
	final IntParam nonMaxSize = intParam(4, 5, "0-100");

	public StarDetectorCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		StarDetector d = StarDetector.create(maxSize.v, th1.v, th2.v, th3.v, nonMaxSize.v);
		d.detect(env.mat, env.keyPoints);
	}

}
