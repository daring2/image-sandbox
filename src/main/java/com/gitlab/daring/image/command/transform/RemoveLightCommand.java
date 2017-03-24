package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_imgproc.blur;

public class RemoveLightCommand extends BaseCommand {

    final IntParam blurRate = intParam(3, "0-255");

    public RemoveLightCommand(String... params) {
        super(params);
    }

    @Override
    public void execute(CommandEnv env) {
        opencv_core.Mat src = env.mat;
        opencv_core.Mat lightMap = new opencv_core.Mat(src.rows(), src.cols(), src.type());
        int br = blurRate.v;
        blur(src, lightMap, new opencv_core.Size(src.cols() / br, src.rows() / br));
        final opencv_core.Mat res = new opencv_core.Mat();
        opencv_core.subtract(src, lightMap, res);
        env.mat = res;
    }

}