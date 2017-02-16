package com.gitlab.daring.image.command.env;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;

public class SetTaskCommand extends BaseCommand {

	public SetTaskCommand(String... args) {
		super(args);
	}

	@Override
	public boolean isEnabled(CommandEnv env) {
		return true;
	}

	@Override
	public void execute(CommandEnv env) {
		env.curTask = arg(0, "");
	}

}