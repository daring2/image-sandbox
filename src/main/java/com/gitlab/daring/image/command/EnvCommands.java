package com.gitlab.daring.image.command;

import com.gitlab.daring.image.command.parameter.EnumParam;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

public class EnvCommands {

	public static void register(CommandRegistry r) {
		EnvCommands f = new EnvCommands();
		r.register("read", f::newReadCommand);
		r.register("write", f::newWriteCommand);
		r.register("get", f::newGetCommand);
		r.register("put", f::newPutCommand);
		r.register("show", ShowCommand::new);
	}

	public Command newReadCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		EnumParam<ReadFlag> flags = c.enumParam(ReadFlag.class, 1);
		c.func = env -> env.setMat(ps[2], imread(ps[0], flags.vi()));
		return c;
	}

	enum ReadFlag { Grey, Color }

	public Command newWriteCommand(String... ps) {
		return env -> imwrite(ps[0], env.getMat(ps[1]));
	}

	public Command newGetCommand(String... ps) {
		return env -> env.getMat(ps[0]).copyTo(env.mat);
	}

	public Command newPutCommand(String... ps) {
		return env -> env.mats.put(ps[0], env.mat.clone());
	}

}
