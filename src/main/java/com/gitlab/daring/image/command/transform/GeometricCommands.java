package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Size;

import static org.bytedeco.javacpp.opencv_imgproc.resize;

public class GeometricCommands {

	public static void register(CommandRegistry r) {
		GeometricCommands f = new GeometricCommands();
		r.register("scale", f::scaleCommand);
	}

	public Command scaleCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		IntParam sp = c.intParam(0, "1-200");
		EnumParam<InterMethod> method = c.enumParam(InterMethod.class, 1);
		Size s0 = new Size();
		return c.withFunc((m, d) -> {
			double f = sp.v * 0.01;
			resize(m, d, s0, f, f, method.vi());
		});
	}

	enum InterMethod { Nearest, Linear, Cubic, Area, Lanczos4 }

}
