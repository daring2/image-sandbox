package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;

import static org.bytedeco.javacpp.opencv_core.CV_8U;
import static org.bytedeco.javacpp.opencv_core.normalize;

public class NormalizeCommand extends BaseCommand {

	final IntParam alpha = intParam(0, "0-255");
	final IntParam beta = intParam(255, "0-255");
	final EnumParam<NormType> normType = enumParam(NormType.class, NormType.MinMax);
	final IntParam dtype = intParam(CV_8U, "0-10");

	final Mat rm = new Mat();
	final Mat mask = new Mat();

	public NormalizeCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		int nt = normType.v.code;
		normalize(env.mat, rm, alpha.v, beta.v, nt, dtype.v, mask);
		env.mat = rm;
	}

	public enum NormType {

		MinMax(32), Inf(1), L19(2), L2(4);

		public final int code;

		NormType(int code) { this.code = code; }

	}

}
