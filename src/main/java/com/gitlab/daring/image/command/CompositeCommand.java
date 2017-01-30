package com.gitlab.daring.image.command;

import java.util.List;
import static com.gitlab.daring.image.util.CommonUtils.closeQuietly;

public class CompositeCommand implements Command, AutoCloseable {

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
		for (Command cmd : commands) {
			if (cmd instanceof AutoCloseable)
				closeQuietly((AutoCloseable) cmd);
		}
	}

}