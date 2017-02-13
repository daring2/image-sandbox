package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;

import static org.bytedeco.javacpp.opencv_core.normalize;

public class NormalizeCommand extends BaseCommand {

	final IntParam alpha = intParam(0, "0-255");
	final IntParam beta = intParam(255, "0-255");
	final EnumParam<NormType> normType = enumParam(NormType.class, NormType.MinMax);

	final Mat mask = new Mat();

	public NormalizeCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat rm = new Mat();
		int nt = normType.v.code;
		normalize(env.mat, rm, alpha.v, beta.v, nt, -1, mask);
		env.mat = rm;
	}

	public enum NormType {

		MinMax(32), Inf(1), L19(2), L2(4);

		public final int code;

		NormType(int code) { this.code = code; }

	}

}
