package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;

import static com.gitlab.daring.image.command.transform.InterMethod.Linear;
import static org.bytedeco.javacpp.opencv_imgproc.resize;

public class ScaleCommand extends BaseCommand {

    final IntParam factor = intParam(100, "0-200");
    final EnumParam<InterMethod> method = enumParam(InterMethod.class, Linear);

    final Mat rm = new Mat();
    final Size s0 = new Size();

    public ScaleCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        if (factor.v == 100) return;
        double f = Math.max(factor.getPv(), 0.005);
        resize(env.mat, rm, s0, f, f, method.vi());
        env.mat = rm;
    }

}