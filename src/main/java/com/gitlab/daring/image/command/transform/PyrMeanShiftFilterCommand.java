package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.TermCriteria;

import static org.bytedeco.javacpp.opencv_imgproc.pyrMeanShiftFiltering;

public class PyrMeanShiftFilterCommand extends BaseCommand {

	final IntParam sp = intParam(0, "0-100");
	final IntParam sr = intParam(1, "0-100");
	final IntParam ml = intParam(2, "0-5");
	final TermCriteria tr = new TermCriteria(3, 5, 1);
	final Mat dm = new Mat();

	public PyrMeanShiftFilterCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		pyrMeanShiftFiltering(env.mat, dm, sp.v, sr.v, ml.v, tr);
		env.mat = dm;
	}

}
