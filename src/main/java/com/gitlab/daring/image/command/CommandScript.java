package com.gitlab.daring.image.command;

import java.util.function.Consumer;

import static com.gitlab.daring.image.command.CommandRegistry.parseCmdScript;

public class CommandScript {

	public final CommandEnv env = new CommandEnv();

	String text;
	CompositeCommand command;

	public CommandScript() {
		setText("");
	}

	public String getText() {
		return text;
	}

	public CompositeCommand getCommand() {
		return command;
	}

	public void execute() {
		command.execute(env);
	}

	public void setText(String text) {
		this.text = text;
		command = parseCmdScript(text);
	}

	public void addParamChangeListener(Consumer<Void> l) {
		command.getParams().forEach(p -> p.changeEvent.addListener(l));
	}

}
