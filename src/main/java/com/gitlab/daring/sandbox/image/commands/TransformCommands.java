package com.gitlab.daring.sandbox.image.commands;

import org.bytedeco.javacpp.opencv_core.Mat;

import static com.gitlab.daring.sandbox.image.commands.CommandUtils.newCommand;
import static com.gitlab.daring.sandbox.image.util.EnumUtils.findEnumIndex;
import static java.lang.Double.parseDouble;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class TransformCommands {

	public static void register(CommandRegistry r) {
		TransformCommands p = new TransformCommands();
		r.register("equalizeHist", p::newEqualizeHistCmd);
		r.register("threshold", p::newThresholdCmd);
		r.register("morphology", p::newMorphologyCmd);
		r.register("canny", p::newCannyCmd);
	}

	public Command newEqualizeHistCmd(String[] args) {
		return newCommand(m -> equalizeHist(m, m));
	}

	public Command newThresholdCmd(String[] args) {
		double th1 = parseDouble(args[0]);
		double maxValue = parseDouble(args[1]);
		int type = findEnumIndex(ThresholdType.values(), args[2]);
		return newCommand(m -> threshold(m, m, th1, maxValue, type));
	}

	enum ThresholdType {
		Bin, BinInv, Trunc, ToZero, ToZeroInv
	}

	public Command newMorphologyCmd(String[] args) {
		int op = findEnumIndex(MorphOperation.values(), args[0]);
		Mat kernel = new Mat();
		return newCommand(m -> morphologyEx(m, m, op, kernel));
	}

	enum MorphOperation {
		Erode, Dilate, Open, Close, Gradient, TopHat, BlackHat, HitMiss
	}

	public Command newCannyCmd(String[] args) {
		double th1 = parseDouble(args[0]);
		double th2 = parseDouble(args[1]);
		return newCommand(m -> Canny(m, m, th1, th2));
	}

}
