package com.gitlab.daring.image.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import org.bytedeco.javacpp.opencv_core.Mat;
import static com.gitlab.daring.image.command.CommandUtils.newCommand;
import static com.gitlab.daring.image.util.EnumUtils.findEnumIndex;
import static java.lang.Double.parseDouble;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class TransformCommands {

	public static void register(CommandRegistry r) {
		TransformCommands f = new TransformCommands();
		r.register("convert", ConvertCommand::new);
		r.register("equalizeHist", f::equalizeHistCmd);
		r.register("morphology", f::morphologyCmd);
		r.register("canny", f::cannyCmd);
		r.register("bitwisePrev",  BitwisePrevCommand::new);
		r.register("filterContours", FilterContoursCommand::new);
		ThresholdCommands.register(r);
		FilterCommands.register(r);
	}

	public Command equalizeHistCmd(String... ps) {
		return newCommand(m -> equalizeHist(m, m));
	}

	public Command morphologyCmd(String... ps) {
		int op = findEnumIndex(MorphOperation.values(), ps[0]);
		Mat kernel = new Mat();
		return newCommand(m -> morphologyEx(m, m, op, kernel));
	}

	enum MorphOperation { Erode, Dilate, Open, Close, Gradient, TopHat, BlackHat, HitMiss }

	public Command cannyCmd(String[] ps) {
		double th1 = parseDouble(ps[0]);
		double th2 = parseDouble(ps[1]);
		return newCommand(m -> Canny(m, m, th1, th2));
	}

}
