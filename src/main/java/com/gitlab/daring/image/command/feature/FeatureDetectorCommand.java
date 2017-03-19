package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;

import static com.gitlab.daring.image.command.feature.FeatureDetectorType.SURF;

public class FeatureDetectorCommand extends BaseCommand {

    final EnumParam<FeatureDetectorType> detectorType = enumParam(FeatureDetectorType.class, SURF);

    public FeatureDetectorCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        FeatureDetectorType dt = detectorType.v;
        env.featureDetector = dt.factory.create();
    }

}
