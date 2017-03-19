package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_features2d.MSER;

class MSERCommand extends BaseCommand {

    //TODO support all params
    final IntParam delta = intParam(5, "0-100");
    final IntParam minArea = intParam(60, "0-1000");
    final IntParam maxArea = intParam(14400, "0-100000");
    final MSER detector = MSER.create();

    MSERCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        detector.setDelta(delta.v);
        detector.setMinArea(minArea.v);
        detector.setMaxArea(maxArea.v);
        env.featureDetector = detector;
    }

}
