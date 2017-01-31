package com.gitlab.daring.image.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static com.gitlab.daring.image.util.EnumUtils.findEnum;

public class BitwisePrevCommand extends BaseCommand {

	final Type type = findEnum(Type.values(), params[0]);
	final Mat curMat = new Mat();
	final Mat prevMat = new Mat();

	public BitwisePrevCommand(String... params) {
		super(params);
	}

	@Override
	public void execute(CommandEnv env) {
		env.mat.copyTo(curMat);
		if (!prevMat.empty()) type.func.apply(prevMat, curMat, env.mat);
		curMat.copyTo(prevMat);
	}

	@FunctionalInterface
	interface BitwiseMethod {
		void apply(Mat m1, Mat m2, Mat dm);
	}

	enum Type {

		AND(opencv_core::bitwise_and),
		OR(opencv_core::bitwise_or),
		NOT(opencv_core::bitwise_not),
		XOR(opencv_core::bitwise_xor);

		final BitwiseMethod func;

		Type(BitwiseMethod func) {
			this.func = func;
		}
	}

}
