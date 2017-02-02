package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;

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
		double th1 = parseDouble(ps[0]);
		double maxValue = parseDouble(ps[1]);
		int type = findEnumIndex(ThresholdType.values(), ps[2]);
		return newCommand(m -> threshold(m, m, th1, maxValue, type));
	}

	public Command adaptiveThresholdCommand(String... ps) {
		double maxValue = parseDouble(ps[0]);
		int method = findEnumIndex(AdaptiveMethod.values(), ps[1]);
		int type = findEnumIndex(ThresholdType.values(), ps[2]);
		int blockSize = parseInt(ps[3]);
		int c = parseInt(ps[4]);
		return newCommand(m -> adaptiveThreshold(m, m, maxValue, method, type, blockSize, c));
	}

	enum ThresholdType { Bin, BinInv, Trunc, ToZero, ToZeroInv }

	enum AdaptiveMethod { Mean, Gaussian }

}
