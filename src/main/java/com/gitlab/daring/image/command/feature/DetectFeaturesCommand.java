package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import org.bytedeco.javacpp.opencv_xfeatures2d.SURF;

public class DetectFeaturesCommand extends BaseCommand {

    public DetectFeaturesCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        if (env.featureDetector == null) env.featureDetector = SURF.create();
        env.featureDetector.detect(env.mat, env.keyPoints);
    }

}