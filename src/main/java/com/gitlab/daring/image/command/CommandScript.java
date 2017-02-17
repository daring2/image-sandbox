package com.gitlab.daring.image.command;

import com.gitlab.daring.image.event.ValueEvent;
import com.gitlab.daring.image.util.CommonUtils;
import com.gitlab.daring.image.util.VoidCallable;

import static com.gitlab.daring.image.command.CommandScriptUtils.parseScript;

public class CommandScript {

	public final CommandEnv env = new CommandEnv();
	public final ValueEvent<Exception> errorEvent = new ValueEvent<>();

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

	public void runTask(String task) {
		env.task = task; env.curTask = "";
		tryRun(() -> command.execute(env));
	}

	public void execute() {
		runTask("");
	}

	public void execute(String script, String task) {
		setText(script);
		runTask(task);
	}

	void tryRun(VoidCallable call) {
		CommonUtils.tryRun(call, errorEvent);
	}

}
