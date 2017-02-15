package com.gitlab.daring.image.command;

import com.gitlab.daring.image.event.ValueEvent;
import com.gitlab.daring.image.util.CommonUtils;
import com.gitlab.daring.image.util.VoidCallable;

import static com.gitlab.daring.image.command.CommandScriptUtils.parseScript;

public class CommandScript {

	public final CommandEnv env = new CommandEnv();
	final ValueEvent<Exception> errorEvent = new ValueEvent<>();

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

	public void setText(String text) {
		tryRun(() -> command = parseScript(text));
	}

	public void execute() {
		tryRun(() -> command.execute(env));
	}

	public void execute(String script) {
		setText(script);
		execute();
	}

	void tryRun(VoidCallable call) {
		CommandEnv.local.set(env);
		CommonUtils.tryRun(call, errorEvent);
		CommandEnv.local.set(null);
	}

}
