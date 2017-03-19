package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.StringParam;
import com.gitlab.daring.image.features.DMatchResult;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;

import java.nio.ByteBuffer;

import static com.gitlab.daring.image.command.CommandScriptUtils.runCommand;
import static org.bytedeco.javacpp.opencv_features2d.DrawMatchesFlags.NOT_DRAW_SINGLE_POINTS;
import static org.bytedeco.javacpp.opencv_features2d.drawMatches;

public class ShowFeatureMatchesCommand extends BaseCommand {

    //TODO limit param
    final StringParam title = stringParam("matches");

    final Scalar color = Scalar.all(-1);
    final ByteBuffer mask = ByteBuffer.allocate(0);
    final Mat rm = new Mat();

    public ShowFeatureMatchesCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        DMatchResult mr = env.matchResult;
        drawMatches(
            mr.points1.mat, mr.points1.points,
            mr.points2.mat, mr.points2.points,
            mr.matchVector, rm,
            color, color, mask, NOT_DRAW_SINGLE_POINTS
        );
        env.mat = rm;
        runCommand(env, "show", title.v);
    }
}
