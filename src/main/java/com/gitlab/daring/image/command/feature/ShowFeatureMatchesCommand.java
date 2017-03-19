package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.StringParam;
import com.gitlab.daring.image.features.DMatchResult;
import org.bytedeco.javacpp.opencv_core.Mat;

import static com.gitlab.daring.image.command.CommandScriptUtils.runCommand;
import static org.bytedeco.javacpp.opencv_features2d.drawMatches;

public class ShowFeatureMatchesCommand extends BaseCommand {

    //TODO limit param
    final StringParam title = stringParam("matches");

    final Mat rm = new Mat();

    public ShowFeatureMatchesCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        DMatchResult mr = env.matchResult;
        drawMatches(mr.points1.mat, mr.points1.points, mr.points2.mat, mr.points2.points, mr.matchVector, rm);
        env.mat = rm;
        runCommand(env, "show", title.v);
    }
}
