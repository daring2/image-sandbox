package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.features.DMatchResult;
import org.bytedeco.javacpp.opencv_core.Mat;

import static com.gitlab.daring.image.command.feature.HomographyMethod.RANSAC;
import static com.gitlab.daring.image.features.FeatureUtils.buildMatchPoints;
import static org.bytedeco.javacpp.opencv_calib3d.findHomography;

public class FindHomographyCommand extends BaseCommand {

    final EnumParam<HomographyMethod> method = enumParam(HomographyMethod.class, RANSAC);
    final DoubleParam reprojThreshold = doubleParam(3, "0-10");
    final IntParam maxIters = intParam(2000, "0-10000");
    final DoubleParam confidence = doubleParam(0.995, "0-1");

    final Mat mask = new Mat();

    public FindHomographyCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        DMatchResult mr = env.matchResult;
        env.mat = findHomography(
            buildMatchPoints(mr.matches, mr.points1.points, true),
            buildMatchPoints(mr.matches, mr.points2.points, false),
            method.v.code, reprojThreshold.v, mask, maxIters.v, confidence.v
        );
    }

}