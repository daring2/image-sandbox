package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.BooleanParam;
import com.gitlab.daring.image.command.parameter.EnumParam;
import org.bytedeco.javacpp.opencv_features2d.BFMatcher;
import org.bytedeco.javacpp.opencv_features2d.Feature2D;
import org.bytedeco.javacpp.opencv_features2d.ORB;
import org.bytedeco.javacpp.opencv_xfeatures2d.SIFT;
import org.bytedeco.javacpp.opencv_xfeatures2d.SURF;

import static com.gitlab.daring.image.command.feature.BFMatcherCommand.NormType.HAMMING;
import static com.gitlab.daring.image.command.feature.BFMatcherCommand.NormType.HAMMING2;

public class BFMatcherCommand extends BaseCommand {

    final EnumParam<NormType> normType = enumParam(NormType.class, NormType.AUTO);
    final BooleanParam crossCheck = boolParam(true);

    public BFMatcherCommand(String... args) {
        super(args);
    }

    @Override
    public void execute(CommandEnv env) {
        NormType nt = normType.v;
        if (nt == NormType.AUTO) nt = selectNormType(env);
        env.descriptorMatcher = new BFMatcher(nt.code, crossCheck.v);
    }

    NormType selectNormType(CommandEnv env) {
        Feature2D fd = env.featureDetector;
        if (fd instanceof SURF || fd instanceof SIFT) {
            return NormType.L2;
        } else if (fd instanceof ORB) {
            ORB orb = (ORB) fd;
            int k = orb.getWTA_K();
            return (k == 3 || k == 4) ? HAMMING2 : HAMMING;
        } else {
            return HAMMING;
        }
    }

    enum NormType {

        AUTO(0), L1(2), L2(4), HAMMING(6), HAMMING2(7);

        public final int code;

        NormType(int code) { this.code = code; }

    }

}
