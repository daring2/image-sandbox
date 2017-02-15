package com.gitlab.daring.image.command.combine;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;
import org.bytedeco.javacpp.opencv_core.Mat;

import static java.lang.Integer.parseInt;

public class BitwisePrevCommand extends BaseCommand {

	final EnumParam<CombineMethod> op = enumParam(CombineMethod.class, CombineMethod.And);
	final int histSize = parseInt(nextArg(1)); //TODO convert to IntParam

	final Mat[] hms = new Mat[histSize + 1];
	int index;

	public BitwisePrevCommand(String... params) {
		super(params);
		for (int i = 0; i < hms.length; i++) hms[i] = new Mat();
	}

	@Override
	public void execute(CommandEnv env) {
		if (env.mat.empty()) return;
		env.mat.copyTo(hms[index]);
		for (int i = 0; i < hms.length; i++) {
			Mat hm = hms[i];
			if (i == index || hm.empty()) continue;
			op.v.func.apply(env.mat, hm, env.mat);
		}
		index = (index + 1) % hms.length;
	}

}
