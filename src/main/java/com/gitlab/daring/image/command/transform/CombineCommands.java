package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.EnumParam;

import static org.bytedeco.javacpp.opencv_core.max;

public class CombineCommands {

	public static void register(CommandRegistry r) {
		CombineCommands f = new CombineCommands();
		r.register("bitwise", f::bitwiseCommand);
		r.register("max", f::maxCommand);
	}

	public Command bitwiseCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		EnumParam<BitwiseOperation> op = c.enumParam(BitwiseOperation.class, 0);
		return c.withCombFunc(ps[1], (m1, m2) -> op.v.func.apply(m1, m2, m1));
	}

	public Command maxCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		return c.withCombFunc(ps[0], (m1, m2) -> max(m1, m2, m1));
	}

}
