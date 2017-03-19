package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_features2d.ORB;

class ORBCommand extends BaseCommand {

    //TODO support all params
    final IntParam maxFeatures = intParam(100, "0-100");
    final DoubleParam scale = doubleParam(12, "0-100");
    final IntParam nlevels = intParam(8, "0-100");
    final ORB detector = ORB.create();

    ORBCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        detector.setMaxFeatures(maxFeatures.v);
        detector.setScaleFactor(scale.v * 0.1);
        detector.setNLevels(nlevels.v);
        env.featureDetector = detector;
    }

}