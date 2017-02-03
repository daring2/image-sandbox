package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;

import static org.bytedeco.javacpp.opencv_imgproc.adaptiveThreshold;
import static org.bytedeco.javacpp.opencv_imgproc.threshold;

public class ThresholdCommands {

	public static void register(CommandRegistry r) {
		ThresholdCommands f = new ThresholdCommands();
		r.register("threshold", f::thresholdCommand);
		r.register("adaptiveThreshold", f::adaptiveThresholdCommand);
	}

	public Command thresholdCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		DoubleParam th = c.doubleParam(0, "0-255");
		DoubleParam mv = c.doubleParam(1, "0-255");
		EnumParam<ThresholdType> type = c.enumParam(ThresholdType.class, 2);
		return c.withFunc(m -> threshold(m, m, th.v, mv.v, type.vi()));
	}

	public Command adaptiveThresholdCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		DoubleParam mv = c.doubleParam(0, "0-255");
		EnumParam<AdaptiveMethod> method = c.enumParam(AdaptiveMethod.class, 1);
		EnumParam<ThresholdType> type = c.enumParam(ThresholdType.class, 2);
		IntParam bs = c.intParam(3, "1-50");
		IntParam cf = c.intParam(4, "0-100");
		return c.withFunc(m ->
			adaptiveThreshold(m, m, mv.v, method.vi(), type.vi(), bs.v * 2 + 1, cf.v)
		);
	}

	enum ThresholdType { Bin, BinInv, Trunc, ToZero, ToZeroInv }

	enum AdaptiveMethod { Mean, Gaussian }

}
