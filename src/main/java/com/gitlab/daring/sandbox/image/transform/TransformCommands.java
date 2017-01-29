package com.gitlab.daring.sandbox.image.transform;

import com.gitlab.daring.sandbox.image.command.Command;
import com.gitlab.daring.sandbox.image.command.CommandRegistry;
import org.bytedeco.javacpp.opencv_core.Mat;

import static com.gitlab.daring.sandbox.image.command.CommandUtils.newCommand;
import static com.gitlab.daring.sandbox.image.util.EnumUtils.findEnumIndex;
import static java.lang.Double.parseDouble;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class TransformCommands {

	public static void register(CommandRegistry r) {
		TransformCommands p = new TransformCommands();
		r.register("convert", ConvertCommand::new);
		r.register("equalizeHist", p::newEqualizeHistCmd);
		r.register("threshold", p::newThresholdCmd);
		r.register("morphology", p::newMorphologyCmd);
		r.register("canny", p::newCannyCmd);
	}

	public Command newEqualizeHistCmd(String[] params) {
		return newCommand(m -> equalizeHist(m, m));
	}

	public Command newThresholdCmd(String[] params) {
		double th1 = parseDouble(params[0]);
		double maxValue = parseDouble(params[1]);
		int type = findEnumIndex(ThresholdType.values(), params[2]);
		return newCommand(m -> threshold(m, m, th1, maxValue, type));
	}

	enum ThresholdType { Bin, BinInv, Trunc, ToZero, ToZeroInv }

	public Command newMorphologyCmd(String[] params) {
		int op = findEnumIndex(MorphOperation.values(), params[0]);
		Mat kernel = new Mat();
		return newCommand(m -> morphologyEx(m, m, op, kernel));
	}

	enum MorphOperation { Erode, Dilate, Open, Close, Gradient, TopHat, BlackHat, HitMiss }

	public Command newCannyCmd(String[] params) {
		double th1 = parseDouble(params[0]);
		double th2 = parseDouble(params[1]);
		return newCommand(m -> Canny(m, m, th1, th2));
	}

}
