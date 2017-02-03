package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Size;

import static com.gitlab.daring.image.command.CommandUtils.newCommand;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
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
		Size size = parseSize(ps[0]);
		return newCommand((m, d) -> blur(m, d, size));
	}

	public Command gaussianBlurCommand(String... ps) {
		Size size = parseSize(ps[0]);
		double sigma = parseDouble(ps[1]);
		return newCommand((m, d) -> GaussianBlur(m, d, size, sigma));
	}

	public Command medianBlurCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		IntParam ksize = c.intParam(0, "1-50");
		return c.withFunc((m, d) -> medianBlur(m ,d, ksize.v * 2 + 1));
	}

	Size parseSize(String p) {
		int v = parseInt(p);
		return new Size(v, v);
	}

}
