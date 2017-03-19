package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.transform.InterMethod;
import com.gitlab.daring.image.features.DMatchResult;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;

import static com.gitlab.daring.image.command.transform.InterMethod.Linear;
import static org.bytedeco.javacpp.opencv_core.BORDER_CONSTANT;
import static org.bytedeco.javacpp.opencv_imgproc.warpPerspective;

public class WarpPerspectiveCommand extends BaseCommand {

    final EnumParam<InterMethod> method = enumParam(InterMethod.class, Linear);

    final Mat rm = new Mat();

    public WarpPerspectiveCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        DMatchResult mr = env.matchResult;
        Mat m1 = mr.points1.mat;
        warpPerspective(m1, rm, env.mat, m1.size(), method.vi(), BORDER_CONSTANT, Scalar.BLACK);
        env.mat = rm;
    }

}