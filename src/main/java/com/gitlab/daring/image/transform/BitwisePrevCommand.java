package com.gitlab.daring.image.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import static com.gitlab.daring.image.util.EnumUtils.findEnum;
import static java.lang.Integer.parseInt;

public class BitwisePrevCommand extends BaseCommand {

	final Type type = findEnum(Type.values(), params[0]);
	final int histSize = parseInt(params[1]);

	final BitwiseFunction func = type.func;
	final Mat[] hms = new Mat[histSize + 1];
	int index;

	public BitwisePrevCommand(String... params) {
		super(params);
		for (int i = 0; i < hms.length; i++) hms[i] = new Mat();
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m = env.mat;
		m.copyTo(hms[index]);
		for (Mat hm : hms) {
			if (!hm.empty()) func.apply(m, hm, m);
		}
		index = (index + 1) % hms.length;
	}

	@FunctionalInterface
	interface BitwiseFunction {
		void apply(Mat m1, Mat m2, Mat dm);
	}

	enum Type {

		AND(opencv_core::bitwise_and),
		OR(opencv_core::bitwise_or),
		NOT(opencv_core::bitwise_not),
		XOR(opencv_core::bitwise_xor);

		final BitwiseFunction func;

		Type(BitwiseFunction func) {
			this.func = func;
		}
	}

}
