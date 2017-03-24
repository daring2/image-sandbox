package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;

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
        r.register("removeLight", RemoveLightCommand::new);
    }

    public Command blurCommand(String... ps) {
        SimpleCommand c = new SimpleCommand(ps);
        KernelSizeParam sp = new KernelSizeParam(c);
        return c.withFunc((m, d) -> blur(m, d, sp.v));
    }

    public Command gaussianBlurCommand(String... ps) {
        SimpleCommand c = new SimpleCommand(ps);
        KernelSizeParam sp = new KernelSizeParam(c);
        DoubleParam sigma = c.doubleParam(0, "0-10");
        IntParam n = c.intParam(1, "0-10");
        return c.withFunc(n, m -> GaussianBlur(m, m, sp.v, sigma.v));
    }

    public Command medianBlurCommand(String... ps) {
        SimpleCommand c = new SimpleCommand(ps);
        KernelSizeParam sp = new KernelSizeParam(c);
        IntParam n = c.intParam(1, "0-10");
        return c.withFunc(n, m -> medianBlur(m, m, sp.w));
    }

    public Command bilateralFilterCommand(String... ps) {
        SimpleCommand c = new SimpleCommand(ps);
        IntParam dp = c.intParam(5, "0-50");
        DoubleParam sp1 = c.doubleParam(10, "0-200");
        DoubleParam sp2 = c.doubleParam(10, "0-200");
        return c.withFunc((m, d) -> bilateralFilter(m, d, dp.v, sp1.v, sp2.v));
    }

    public Command sharpenCommand(String... ps) {
        SimpleCommand c = new SimpleCommand(ps);
        IntParam n = c.intParam(1, "0-10");
        Mat rm = new Mat();
        return c.withFunc(n, m -> {
            GaussianBlur(m, rm, new Size(), 3);
            addWeighted(m, 1.5, rm, -0.5, 0, m);
        });
    }

}
