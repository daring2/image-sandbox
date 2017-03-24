package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;

import static org.bytedeco.javacpp.opencv_core.subtract;
import static org.bytedeco.javacpp.opencv_imgproc.blur;

public class RemoveLightCommand extends BaseCommand {

    final IntParam blurRate = intParam(3, "0-255");

    final Mat lightMap = new Mat();
    final Mat rm = new Mat();

    public RemoveLightCommand(String... params) {
        super(params);
    }

    @Override
    public void execute(CommandEnv env) {
        Mat src = env.mat;
        int br = blurRate.v;
        blur(src, lightMap, new Size(src.cols() / br, src.rows() / br));
        subtract(src, lightMap, rm);
        env.mat = rm;
    }

}