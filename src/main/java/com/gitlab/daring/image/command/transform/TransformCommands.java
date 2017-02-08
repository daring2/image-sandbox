package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.EnumParam;

import static com.gitlab.daring.image.command.CommandUtils.newCommand;
import static org.bytedeco.javacpp.opencv_imgproc.equalizeHist;

public class TransformCommands {

	public static void register(CommandRegistry r) {
		TransformCommands f = new TransformCommands();
		r.register("equalizeHist", f::equalizeHistCommand);
		r.register("bitwise", f::bitwiseCommand);
		r.register("convert", ConvertCommand::new);
		r.register("morphology", MorphologyCommand::new);
		r.register("bitwisePrev",  BitwisePrevCommand::new);
		r.register("filterContours", FilterContoursCommand::new);
		r.register("pyrMeanShiftFilter", PyrMeanShiftFilterCommand::new);
		ThresholdCommands.register(r);
		FilterCommands.register(r);
		GeometricCommands.register(r);
		ContourCommands.register(r);
	}

	public Command equalizeHistCommand(String... ps) {
		return newCommand(m -> equalizeHist(m, m));
	}

	public Command bitwiseCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		EnumParam<BitwiseOperation> op = c.enumParam(BitwiseOperation.class,0);
		c.func = env -> op.v.func.apply(env.mat, env.getMat(ps[1]), env.mat);
		return c;
	}

}
