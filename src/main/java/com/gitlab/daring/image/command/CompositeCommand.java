package com.gitlab.daring.image.command;

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

	@Override
	public void close()  {
		commands.forEach(Command::close);
	}

}