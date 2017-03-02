package com.gitlab.daring.image.command.combine;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.StringParam;
import org.bytedeco.javacpp.opencv_core.Mat;

import static org.bytedeco.javacpp.opencv_core.addWeighted;

public class AddWeightedCommand extends BaseCommand {

	final StringParam key = stringParam("");
	final DoubleParam f1 = doubleParam(50, "0-100");
	final DoubleParam f2 = doubleParam(50, "0-100");
	final DoubleParam f3 = doubleParam(0, "0-100");

	public AddWeightedCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m2 = env.getMat(key.v);
		addWeighted(env.mat, f1.pv(), m2, f2.pv(), f3.pv(), env.mat);
	}

}