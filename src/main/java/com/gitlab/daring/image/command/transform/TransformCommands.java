package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.EnumParam;
import org.bytedeco.javacpp.opencv_core.Mat;

import static com.gitlab.daring.image.command.CommandUtils.newCommand;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class TransformCommands {

	public static void register(CommandRegistry r) {
		TransformCommands f = new TransformCommands();
		r.register("convert", ConvertCommand::new);
		r.register("equalizeHist", f::equalizeHistCommand);
		r.register("morphology", f::morphologyCommand);
		r.register("canny", f::cannyCommand);
		r.register("bitwisePrev",  BitwisePrevCommand::new);
		r.register("filterContours", FilterContoursCommand::new);
		ThresholdCommands.register(r);
		FilterCommands.register(r);
		GeometricCommands.register(r);
	}

	public Command equalizeHistCommand(String... ps) {
		return newCommand(m -> equalizeHist(m, m));
	}

	public Command morphologyCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		EnumParam<MorphOperation> op = c.enumParam(MorphOperation.class, 0);
		Mat kernel = new Mat();
		return c.withFunc(m -> morphologyEx(m, m, op.vi(), kernel));
	}

	enum MorphOperation { Erode, Dilate, Open, Close, Gradient, TopHat, BlackHat, HitMiss }

	public Command cannyCommand(String[] ps) {
		SimpleCommand c = new SimpleCommand(ps);
		DoubleParam th1 = c.doubleParam(0, "0-500");
		DoubleParam th2 = c.doubleParam(1, "0-500");
		return c.withFunc(m -> Canny(m, m, th1.v, th2.v));
	}

}
