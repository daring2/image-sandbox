package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;

import static java.lang.Double.parseDouble;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class FilterCommands {

	public static void register(CommandRegistry r) {
		FilterCommands f = new FilterCommands();
		r.register("blur", f::blurCommand);
		r.register("gaussianBlur", f::gaussianBlurCommand);
		r.register("medianBlur", f::medianBlurCommand);
		//TODO bilateralFilter
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
		return c.withFunc((m, d) -> medianBlur(m ,d, sp.w));
	}

}
