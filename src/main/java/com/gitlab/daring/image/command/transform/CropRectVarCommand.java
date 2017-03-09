package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.StringParam;
import org.bytedeco.javacpp.opencv_core.Rect;

import java.awt.*;

import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;

public class CropRectVarCommand extends BaseCommand {

    final StringParam rectKey = stringParam("rect");
    final StringParam matKey = stringParam("");

    public CropRectVarCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        String mk = matKey.v;
        Rect rect = toOpencv((Rectangle) env.vars.get(rectKey.v));
        env.putMat(mk, env.getMat(mk).apply(rect));
    }

}
