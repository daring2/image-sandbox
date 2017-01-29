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
		TransformCommands i = new TransformCommands();
		r.register("convert", ConvertCommand::new);
		r.register("equalizeHist", i::newEqualizeHistCmd);
		r.register("morphology", i::newMorphologyCmd);
		r.register("canny", i::newCannyCmd);
		ThresholdCommands.register(r);
	}

	public Command newEqualizeHistCmd(String[] ps) {
		return newCommand(m -> equalizeHist(m, m));
	}


	public Command newMorphologyCmd(String[] ps) {
		int op = findEnumIndex(MorphOperation.values(), ps[0]);
		Mat kernel = new Mat();
		return newCommand(m -> morphologyEx(m, m, op, kernel));
	}

	enum MorphOperation { Erode, Dilate, Open, Close, Gradient, TopHat, BlackHat, HitMiss }

	public Command newCannyCmd(String[] ps) {
		double th1 = parseDouble(ps[0]);
		double th2 = parseDouble(ps[1]);
		return newCommand(m -> Canny(m, m, th1, th2));
	}

}
