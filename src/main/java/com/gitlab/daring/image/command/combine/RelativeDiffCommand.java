package com.gitlab.daring.image.command.combine;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.StringParam;
import org.bytedeco.javacpp.opencv_core.*;

import static org.bytedeco.javacpp.opencv_core.*;

public class RelativeDiffCommand extends BaseCommand {

    final StringParam mkey = stringParam("");

    final Mat rm = new Mat();
    final Mat diffMat = new Mat();

    public RelativeDiffCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        Mat m1 = env.mat, m2 = env.getMat(mkey.v);
        absdiff(m1, m2, diffMat);
        MatExpr m3 = multiply(divide(diffMat, max(m1, m2)), 255);
        m3.asMat().convertTo(rm, CV_8U);
        env.mat = rm;
    }

}