package com.gitlab.daring.image.command;

import com.gitlab.daring.image.command.parameter.CommandParam;
import one.util.streamex.StreamEx;

import java.util.List;

public class ScriptCommand implements Command {

	public final String script;
	public final List<Command> commands;

	public ScriptCommand(String script, List<Command> commands) {
		this.script = script;
		this.commands = commands;
	}

	@Override
	public void execute(CommandEnv env) {
		commands.forEach(c -> c.execute(env));
	}

	@Override
	public List<CommandParam<?>> getParams() {
		return StreamEx.of(commands).toFlatList(Command::getParams);
	}

	@Override
	public void close()  {
		commands.forEach(Command::close);
	}

}