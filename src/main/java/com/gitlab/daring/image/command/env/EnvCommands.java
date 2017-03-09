package com.gitlab.daring.image.command.env;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;

import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

public class EnvCommands {

    public static void register(CommandRegistry r) {
        EnvCommands f = new EnvCommands();
        r.register("write", f::newWriteCommand);
        r.register("get", f::newGetCommand);
        r.register("put", f::newPutCommand);
        r.register("read", ReadCommand::new);
        r.register("show", ShowCommand::new);
        r.register("task", SetTaskCommand::new);
    }

    public Command newWriteCommand(String... ps) {
        SimpleCommand c = new SimpleCommand(ps);
        String file = c.arg(0, "");
        String key = c.arg(1, "");
        return env -> imwrite(env.eval(file), env.getMat(key));
    }

    public Command newGetCommand(String... ps) {
        return env -> env.getMat(ps[0]).copyTo(env.mat);
    }

    public Command newPutCommand(String... ps) {
        return env -> env.putMat(ps[0], env.mat);
    }

}
