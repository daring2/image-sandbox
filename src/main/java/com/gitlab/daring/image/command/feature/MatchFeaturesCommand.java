package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.StringParam;
import com.gitlab.daring.image.features.DMatchResult;
import com.gitlab.daring.image.features.KeyPointList;
import org.bytedeco.javacpp.opencv_core.DMatchVector;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_features2d.DescriptorMatcher;
import org.bytedeco.javacpp.opencv_features2d.Feature2D;
import org.bytedeco.javacpp.opencv_xfeatures2d.SURF;

import static com.gitlab.daring.image.command.CommandScriptUtils.runCommand;
import static com.gitlab.daring.image.features.FeatureUtils.detectAndCompute;

public class MatchFeaturesCommand extends BaseCommand {

    final StringParam matKey = stringParam("");

    public MatchFeaturesCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        Mat m1 = env.mat, m2 = env.getMat(matKey.v);

        Feature2D fd = env.featureDetector;
        if (fd == null) fd = SURF.create();
        KeyPointList ps1 = detectAndCompute(fd, m1);
        KeyPointList ps2 = detectAndCompute(fd, m2);

        if (env.descriptorMatcher == null)
            runCommand(env, "newBFMatcher");
        DescriptorMatcher dm = env.descriptorMatcher;
        DMatchVector mv = new DMatchVector();
        dm.match(ps1.descriptors, ps2.descriptors, mv);
        env.matchResult = new DMatchResult(ps1, ps2, mv);
    }

}
