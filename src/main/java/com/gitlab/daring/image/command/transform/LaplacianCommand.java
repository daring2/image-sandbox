package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.*;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.Laplacian;

public class LaplacianCommand extends BaseCommand {

	final IntParam ksize = intParam(2, "1-10");
	final Mat rm = new Mat();

	public LaplacianCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		int kf = ksize.v * 2 + 1;
		Laplacian(env.mat, rm, CV_32F, kf, 1, 0, BORDER_DEFAULT);
		rm.convertTo(env.mat, CV_8U);
	}

}