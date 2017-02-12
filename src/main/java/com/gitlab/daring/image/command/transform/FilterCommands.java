package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;

import static java.lang.Double.parseDouble;
import static org.bytedeco.javacpp.opencv_core.addWeighted;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class FilterCommands {

	public static void register(CommandRegistry r) {
		FilterCommands f = new FilterCommands();
		r.register("blur", f::blurCommand);
		r.register("gaussianBlur", f::gaussianBlurCommand);
		r.register("medianBlur", f::medianBlurCommand);
		r.register("bilateralFilter", f::bilateralFilterCommand);
		r.register("sharpen", f::sharpenCommand);
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
		double sigma = parseDouble(c.arg(1, 0));
		return c.withFunc((m, d) -> GaussianBlur(m, d, sp.v, sigma));
	}

	public Command medianBlurCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		KernelSizeParam sp = new KernelSizeParam(c, 0);
		IntParam n = c.intParam(1, 1, "0-10");
		return c.withFunc(n.v, m -> medianBlur(m.clone(), m, sp.w));
	}

	public Command bilateralFilterCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		IntParam dp = c.intParam(0, 5, "0-50");;
		DoubleParam sp1  = c.doubleParam(1, 10, "0-200");
		DoubleParam sp2  = c.doubleParam(2, 10, "0-200");
		return c.withFunc((m, d) -> bilateralFilter(m, d, dp.v, sp1.v, sp2.v));
	}

	public Command sharpenCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		IntParam n = c.intParam(0, 1, "0-10");
		Mat rm = new Mat();
		return c.withFunc(n.v, m -> {
			GaussianBlur(m, rm, new Size(), 3);
			addWeighted(m, 1.5, rm, -0.5, 0, m);
		});
	}

}
