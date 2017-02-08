package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.EnumParam;

public class CombineCommands {

	public static void register(CommandRegistry r) {
		CombineCommands f = new CombineCommands();
		r.register("bitwise", f::bitwiseCommand);
	}

	public Command bitwiseCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		EnumParam<BitwiseOperation> op = c.enumParam(BitwiseOperation.class,0);
		c.func = env -> op.v.func.apply(env.mat, env.getMat(ps[1]), env.mat);
		return c;
	}

}
