package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.BooleanParam;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.IntParam;

import static org.bytedeco.javacpp.opencv_imgproc.Canny;
import static org.bytedeco.javacpp.opencv_imgproc.cornerHarris;

public class StructureCommands {

	public static void register(CommandRegistry r) {
		StructureCommands f = new StructureCommands();
		r.register("canny", f::cannyCommand);
		r.register("cornerHarris", f::cornerHarrisCommand);
		r.register("findContours", FindContoursCommand::new);
		r.register("filterContours", FilterContoursCommand::new);
		r.register("drawContours", DrawContoursCommand::new);
		r.register("showContours", ShowContoursCommand::new);
		r.register("watershedCenter", WatershedCenterCommand::new);
		r.register("watershedMarker", WatershedMarkerCommand::new);
		r.register("pyrMeanShiftFilter", PyrMeanShiftFilterCommand::new);
		r.register("grubCutCenter", GrubCutCenterCommand::new);
		r.register("houghCircles", HoughCirclesCommand::new);
		r.register("detectLines", DetectLinesCommand::new);
	}

	public Command cannyCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		DoubleParam th1 = c.doubleParam(100, "0-500");
		DoubleParam th2 = c.doubleParam(200,"0-500");
		IntParam sp = c.intParam(1, "0-50");
		BooleanParam l2g = c.boolParam(false);
		return c.withFunc(m -> Canny(m, m, th1.v, th2.v, sp.v * 2 + 1, l2g.v));
	}

	public Command cornerHarrisCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		IntParam b = c.intParam(2, "0-10");
		IntParam ksp = c.intParam(1, "0-10");
		DoubleParam k = c.doubleParam(4, "0-100");
		return c.withFunc((m, d) -> cornerHarris(m, d, b.v, ksp.v * 2 + 1, k.v * 0.01));
	}

}
