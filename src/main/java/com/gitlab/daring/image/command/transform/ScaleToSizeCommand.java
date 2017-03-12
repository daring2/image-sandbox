package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;

import static com.gitlab.daring.image.command.transform.InterMethod.Linear;
import static org.bytedeco.javacpp.opencv_imgproc.resize;

public class ScaleToSizeCommand extends BaseCommand {

    final IntParam matSize = intParam(0, "0-5000");
    final IntParam minFactor = intParam(5, "0-100");
    final EnumParam<InterMethod> method = enumParam(InterMethod.class, Linear);

    final Mat rm = new Mat();
    final Size s0 = new Size();

    public ScaleToSizeCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        Mat m = env.mat;
        double f = 1.0 * matSize.v / Math.max(m.rows(), m.cols());
        if (f < minFactor.pv()) return;
        resize(m, rm, s0, f, f, method.vi());
        env.mat = rm;
    }

}