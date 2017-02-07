package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.BooleanParam;
import com.gitlab.daring.image.command.parameter.DoubleParam;

import static com.gitlab.daring.image.command.CommandUtils.newCommand;
import static org.bytedeco.javacpp.opencv_imgproc.Canny;
import static org.bytedeco.javacpp.opencv_imgproc.equalizeHist;

public class TransformCommands {

	public static void register(CommandRegistry r) {
		TransformCommands f = new TransformCommands();
		r.register("convert", ConvertCommand::new);
		r.register("equalizeHist", f::equalizeHistCommand);
		r.register("canny", f::cannyCommand);
		r.register("morphology", MorphologyCommand::new);
		r.register("bitwisePrev",  BitwisePrevCommand::new);
		r.register("filterContours", FilterContoursCommand::new);
		r.register("pyrMeanShiftFilter", PyrMeanShiftFilterCommand::new);
		ThresholdCommands.register(r);
		FilterCommands.register(r);
		GeometricCommands.register(r);
	}

	public Command equalizeHistCommand(String... ps) {
		return newCommand(m -> equalizeHist(m, m));
	}

	public Command cannyCommand(String[] ps) {
		SimpleCommand c = new SimpleCommand(ps);
		DoubleParam th1 = c.doubleParam(0, "0-500");
		DoubleParam th2 = c.doubleParam(1, "0-500");
		KernelSizeParam sp = new KernelSizeParam(c, 2);
		BooleanParam l2g = c.boolParam(3);
		return c.withFunc(m -> Canny(m, m, th1.v, th2.v, sp.w, l2g.v));
	}

}
