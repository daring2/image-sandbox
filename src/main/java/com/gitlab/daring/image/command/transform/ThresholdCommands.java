package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;

import static com.gitlab.daring.image.util.ImageUtils.smat;
import static org.bytedeco.javacpp.opencv_core.inRange;
import static org.bytedeco.javacpp.opencv_imgproc.adaptiveThreshold;
import static org.bytedeco.javacpp.opencv_imgproc.threshold;

public class ThresholdCommands {

    public static void register(CommandRegistry r) {
        ThresholdCommands f = new ThresholdCommands();
        r.register("threshold", f::thresholdCommand);
        r.register("adaptiveThreshold", f::adaptiveThresholdCommand);
        r.register("inRange", f::inRangeCommand);
    }

    public Command thresholdCommand(String... ps) {
        SimpleCommand c = new SimpleCommand(ps);
        DoubleParam th = c.doubleParam(128, "0-255");
        DoubleParam mv = c.doubleParam(255, "0-255");
        EnumParam<ThresholdType> tp = c.enumParam(ThresholdType.class, ThresholdType.Bin);
        EnumParam<ThresholdFlag> fp = c.enumParam(ThresholdFlag.class, ThresholdFlag.None);
        return c.withFunc(m -> threshold(m, m, th.v, mv.v, tp.vi() + fp.vi() * 8));
    }

    public Command adaptiveThresholdCommand(String... ps) {
        SimpleCommand c = new SimpleCommand(ps);
        DoubleParam mv = c.doubleParam(255, "0-255");
        EnumParam<AdaptiveMethod> method = c.enumParam(AdaptiveMethod.class, AdaptiveMethod.Mean);
        EnumParam<ThresholdType> type = c.enumParam(ThresholdType.class, ThresholdType.Bin);
        IntParam bs = c.intParam(1, "0-50");
        IntParam cf = c.intParam(0, "0-100");
        return c.withFunc(m ->
            adaptiveThreshold(m, m, mv.v, method.vi(), type.vi(), bs.v * 2 + 1, cf.v)
        );
    }

    public Command inRangeCommand(String... ps) {
        SimpleCommand c = new SimpleCommand(ps);
        IntParam lb = c.intParam(0, "0-255");
        IntParam ub = c.intParam(255, "0-255");
        return c.withFunc(m -> inRange(m, smat(lb.v), smat(ub.v), m));
    }

    enum ThresholdType {Bin, BinInv, Trunc, ToZero, ToZeroInv}

    enum ThresholdFlag {None, OTSU, Triangle}

    enum AdaptiveMethod {Mean, Gaussian}

}
