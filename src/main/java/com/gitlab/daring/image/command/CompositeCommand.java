package com.gitlab.daring.image.command;

import com.gitlab.daring.image.command.parameter.CommandParam;

import java.util.List;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

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
	public List<CommandParam<?>> getParams() {
		return commands.stream().flatMap(c -> c.getParams().stream()).collect(toList());
	}

	public void addParamChangeListener(Consumer<Void> l) {
		getParams().forEach(p -> p.changeEvent.addListener(l));
	}

	@Override
	public void close()  {
		commands.forEach(Command::close);
	}

}