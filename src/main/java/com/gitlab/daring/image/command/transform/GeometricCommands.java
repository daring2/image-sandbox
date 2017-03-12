package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Rect;

import static com.gitlab.daring.image.command.CommandUtils.parseIntParams;
import static com.gitlab.daring.image.util.ImageUtils.cropCenter;

public class GeometricCommands {

    public static void register(CommandRegistry r) {
        GeometricCommands f = new GeometricCommands();
        r.register("cropRect", f::cropRectCommand);
        r.register("cropCenter", f::cropCenterCommand);
        r.register("scale", ScaleCommand::new);
        r.register("scaleToSize", ScaleToSizeCommand::new);
        r.register("cropRectVar", CropRectVarCommand::new);
    }

    public Command cropRectCommand(String... args) {
        SimpleCommand c = new SimpleCommand(args);
        int[] ps = parseIntParams(args);
        Rect rect = new Rect(ps[0], ps[1], ps[2], ps[3]);
        return c.withSetFunc(m -> m.apply(rect));
    }

    public Command cropCenterCommand(String... ps) {
        SimpleCommand c = new SimpleCommand(ps);
        IntParam sp = c.intParam(100, "0-100");
        return c.withSetFunc(m -> cropCenter(m, sp.pv()));
    }

}
