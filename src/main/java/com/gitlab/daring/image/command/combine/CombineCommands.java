package com.gitlab.daring.image.command.combine;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.StringParam;

public class CombineCommands {

	public static void register(CommandRegistry r) {
		CombineCommands f = new CombineCommands();
		r.register("combine", f::combineCommand);
		r.register("addWeighted", AddWeightedCommand::new);
	}

	public Command combineCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		StringParam mk = c.stringParam("");
		EnumParam<CombineMethod> op = c.enumParam(CombineMethod.class, null);
		return c.withCombFunc(mk.v, (m1, m2) -> op.v.func.apply(m1, m2, m1));
	}

}
