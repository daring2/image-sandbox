package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.EnumParam;

import static com.gitlab.daring.image.command.CommandUtils.newCommand;
import static com.gitlab.daring.image.util.EnumUtils.findEnumIndex;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
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
		c.setFunc(m -> threshold(m, m, th.v, mv.v, type.index()));
		return c;
	}

	public Command adaptiveThresholdCommand(String... ps) {
		double maxValue = parseDouble(ps[0]);
		int method = findEnumIndex(AdaptiveMethod.class, ps[1]);
		int type = findEnumIndex(ThresholdType.class, ps[2]);
		int blockSize = parseInt(ps[3]);
		int c = parseInt(ps[4]);
		return newCommand(m -> adaptiveThreshold(m, m, maxValue, method, type, blockSize, c));
	}

	enum ThresholdType { Bin, BinInv, Trunc, ToZero, ToZeroInv }

	enum AdaptiveMethod { Mean, Gaussian }

}
