package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;

import static org.bytedeco.javacpp.opencv_imgproc.morphologyEx;

public class MorphologyCommand extends BaseCommand {

	final EnumParam<Operation> operation = enumParam(Operation.class, null);
	final IntParam iterCount = intParam(1, "0-10");
	final Mat kernel = new Mat();

	public MorphologyCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		for (int i = 0; i < iterCount.v; i++)
			morphologyEx(env.mat, env.mat, operation.vi(), kernel);
	}

	enum Operation { Erode, Dilate, Open, Close, Gradient, TopHat, BlackHat, HitMiss }

}
