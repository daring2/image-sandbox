package com.gitlab.daring.image.command.combine;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import org.bytedeco.javacpp.opencv_core.Mat;

import static java.lang.Double.parseDouble;
import static org.bytedeco.javacpp.opencv_core.addWeighted;

public class AddWeightedCommand extends BaseCommand {

	public AddWeightedCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m2 = env.getMat(args[0]);
		double f1 = parseDouble(args[1]);
		double f2 = parseDouble(args[2]);
		double f3 = parseDouble(args[3]);
		addWeighted(env.mat, f1, m2, f2, f3, env.mat);
	}

}