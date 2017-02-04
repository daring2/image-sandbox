package com.gitlab.daring.image.command;

import java.util.function.Consumer;

import static com.gitlab.daring.image.command.CommandRegistry.parseCmdScript;

public class CommandScript {

	public final CommandEnv env = new CommandEnv();

	volatile ScriptCommand command;

	public CommandScript() {
		setText("");
	}

	public String getText() {
		return command.script;
	}

	public ScriptCommand getCommand() {
		return command;
	}

	public void execute() {
		command.execute(env);
	}

	public void setText(String text) {
		command = parseCmdScript(text);
	}

	public void addParamChangeListener(Consumer<Void> l) {
		command.getParams().forEach(p -> p.changeEvent.addListener(l));
	}

}
