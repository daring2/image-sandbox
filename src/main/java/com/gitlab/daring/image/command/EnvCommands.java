package com.gitlab.daring.image.command;

import static com.gitlab.daring.image.command.CommandUtils.newCommand;
import static com.gitlab.daring.image.util.EnumUtils.findEnumIndex;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

public class EnvCommands {

	public static void register(CommandRegistry r) {
		EnvCommands f = new EnvCommands();
		r.register("read", f::readCommand);
		r.register("write", f::writeCommand);
		r.register("get", f::getCommand);
		r.register("put", f::putCommand);
		r.register("show", ShowCommand::new);
	}

	public Command readCommand(String... ps) {
		int flags = findEnumIndex(ReadFlag.values(), ps[1]);
		return env -> env.mat = imread(ps[0], flags);
	}

	enum ReadFlag { Grey, Color }

	public Command writeCommand(String... ps) {
		return newCommand(m -> imwrite(ps[0], m));
	}

	public Command getCommand(String... ps) {
		return env -> env.vars.get(ps[0]).copyTo(env.mat);
	}

	public Command putCommand(String... ps) {
		return env -> env.vars.put(ps[0], env.mat.clone());
	}

}
