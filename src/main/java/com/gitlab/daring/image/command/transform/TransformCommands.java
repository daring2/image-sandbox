package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.combine.BitwisePrevCommand;
import com.gitlab.daring.image.command.parameter.IntParam;

import static com.gitlab.daring.image.command.CommandUtils.newCommand;
import static com.gitlab.daring.image.util.ImageUtils.smat;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.equalizeHist;

public class TransformCommands {

    public static void register(CommandRegistry r) {
        TransformCommands f = new TransformCommands();
        r.register("equalizeHist", f::equalizeHistCommand);
        r.register("bitwiseNot", f::bitwiseNotCommand);
        r.register("keepRange", f::keepRange);
        r.register("convert", ConvertCommand::new);
        r.register("morphology", MorphologyCommand::new);
        r.register("bitwisePrev", BitwisePrevCommand::new);
        r.register("normalize", NormalizeCommand::new);
        ThresholdCommands.register(r);
        FilterCommands.register(r);
        GeometricCommands.register(r);
    }

    public Command equalizeHistCommand(String... ps) {
        return newCommand(m -> equalizeHist(m, m));
    }

    public Command bitwiseNotCommand(String... ps) {
        return newCommand(m -> bitwise_not(m, m));
    }

    public Command keepRange(String... ps) {
        SimpleCommand c = new SimpleCommand(ps);
        IntParam lb = c.intParam(0, "0-255");
        IntParam ub = c.intParam(255, "0-255");
        return c.withFunc((m, d) -> {
            inRange(m, smat(lb.v), smat(ub.v), d); min(m, d, d);
        });
    }

}
