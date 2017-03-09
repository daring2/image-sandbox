package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;

import static com.gitlab.daring.image.command.transform.ConvertCommand.Target.Grey;
import static com.gitlab.daring.image.util.ImageUtils.buildMat;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class ConvertCommand extends BaseCommand {

    final EnumParam<Target> target = enumParam(Target.class, Target.Grey);

    public ConvertCommand(String... params) {
        super(params);
    }

    @Override
    public void execute(CommandEnv env) {
        if ((env.mat.channels() == 1) != (target.v == Grey)) {
            int code = target.v == Grey ? COLOR_BGR2GRAY : COLOR_GRAY2BGR;
            env.mat = buildMat(m -> cvtColor(env.mat, m, code));
        }
    }

    enum Target {Grey, Color}

}
