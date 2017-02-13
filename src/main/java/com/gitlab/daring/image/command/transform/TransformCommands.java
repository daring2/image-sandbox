package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.combine.BitwisePrevCommand;

import static com.gitlab.daring.image.command.CommandUtils.newCommand;
import static org.bytedeco.javacpp.opencv_core.bitwise_not;
import static org.bytedeco.javacpp.opencv_imgproc.equalizeHist;

public class TransformCommands {

	public static void register(CommandRegistry r) {
		TransformCommands f = new TransformCommands();
		r.register("equalizeHist", f::equalizeHistCommand);
		r.register("bitwiseNot", f::bitwiseNotCommand);
		r.register("convert", ConvertCommand::new);
		r.register("morphology", MorphologyCommand::new);
		r.register("bitwisePrev",  BitwisePrevCommand::new);
		r.register("normalize",  NormalizeCommand::new);
		ThresholdCommands.register(r);
		FilterCommands.register(r);
		GeometricCommands.register(r);
	}

	public Command equalizeHistCommand(String... ps) {
		return newCommand(m -> equalizeHist(m, m));
	}

	public Command bitwiseNotCommand(String... ps) {
		return newCommand(m -> bitwise_not(m, m));
	}

}
