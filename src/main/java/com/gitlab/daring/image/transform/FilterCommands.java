package com.gitlab.daring.image.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import org.bytedeco.javacpp.opencv_core.Size;
import static com.gitlab.daring.image.command.CommandUtils.newCommand;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class FilterCommands {

	public static void register(CommandRegistry r) {
		FilterCommands f = new FilterCommands();
		r.register("blur", f::blurCmd);
		r.register("gaussianBlur", f::gaussianBlurCmd);
		r.register("medianBlur", f::medianBlurCmd);
		//TODO bilateralFilter
	}

	public Command blurCmd(String... ps) {
		Size size = parseSize(ps[0]);
		return newCommand((m, d) -> blur(m, d, size));
	}

	public Command gaussianBlurCmd(String... ps) {
		Size size = parseSize(ps[0]);
		double sigma = parseDouble(ps[1]);
		return newCommand((m, d) -> GaussianBlur(m, d, size, sigma));
	}

	public Command medianBlurCmd(String... ps) {
		int ksize = parseInt(ps[0]);
		return newCommand((m, d) -> medianBlur(m ,d, ksize));
	}

	Size parseSize(String p) {
		int v = parseInt(p);
		return new Size(v, v);
	}

}
