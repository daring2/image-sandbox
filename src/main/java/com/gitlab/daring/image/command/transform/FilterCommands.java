package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.IntParam;

import static java.lang.Double.parseDouble;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class FilterCommands {

	public static void register(CommandRegistry r) {
		FilterCommands f = new FilterCommands();
		r.register("blur", f::blurCommand);
		r.register("gaussianBlur", f::gaussianBlurCommand);
		r.register("medianBlur", f::medianBlurCommand);
		r.register("bilateralFilter", f::bilateralFilterCommand);
		r.register("laplacian", LaplacianCommand::new);
	}

	public Command blurCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		KernelSizeParam sp = new KernelSizeParam(c, 0);
		return c.withFunc((m, d) -> blur(m, d, sp.v));
	}

	public Command gaussianBlurCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		KernelSizeParam sp = new KernelSizeParam(c, 0);
		double sigma = parseDouble(ps[1]);
		return c.withFunc((m, d) -> GaussianBlur(m, d, sp.v, sigma));
	}

	public Command medianBlurCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		KernelSizeParam sp = new KernelSizeParam(c, 0);
		IntParam n = c.intParam(1, "0-10");
		return c.withFunc(m -> {
			for (int i = 0; i < n.v; i++) medianBlur(m.clone(), m, sp.w);
		});
	}

	public Command bilateralFilterCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		IntParam dp = c.intParam(0, "0-50");;
		DoubleParam sp1  = c.doubleParam(1, "0-200");
		DoubleParam sp2  = c.doubleParam(2, "0-200");
		return c.withFunc((m, d) -> bilateralFilter(m, d, dp.v, sp1.v, sp2.v));
	}

}
