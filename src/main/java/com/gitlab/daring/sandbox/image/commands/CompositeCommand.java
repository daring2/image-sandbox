package com.gitlab.daring.sandbox.image.commands;

import java.util.List;

public class CompositeCommand implements Command {

	final List<Command> commands;

	public CompositeCommand(List<Command> commands) {
		this.commands = commands;
	}

	@Override
	public void execute(CommandEnv env) {
		commands.forEach(c -> c.execute(env));
	}

}