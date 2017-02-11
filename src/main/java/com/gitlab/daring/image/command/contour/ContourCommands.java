package com.gitlab.daring.image.command.contour;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.BooleanParam;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.command.transform.WatershedCenterCommand;

import static org.bytedeco.javacpp.opencv_imgproc.Canny;

public class ContourCommands {

	public static void register(CommandRegistry r) {
		ContourCommands f = new ContourCommands();
		r.register("canny", f::cannyCommand);
		r.register("watershedCenter", WatershedCenterCommand::new);
	}

	public Command cannyCommand(String[] ps) {
		SimpleCommand c = new SimpleCommand(ps);
		DoubleParam th1 = c.doubleParam(0, 100, "0-500");
		DoubleParam th2 = c.doubleParam(1, 200,"0-500");
		IntParam sp = c.intParam(2, 1, "0-50");
		BooleanParam l2g = c.boolParam(3, false);
		return c.withFunc(m -> Canny(m, m, th1.v, th2.v, sp.v * 2 + 1, l2g.v));
	}

}
