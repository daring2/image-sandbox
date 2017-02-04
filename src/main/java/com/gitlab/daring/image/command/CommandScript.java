package com.gitlab.daring.image.command;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import static com.gitlab.daring.image.command.CommandRegistry.parseCmdScript;

public class CommandScript {

	public final CommandEnv env = new CommandEnv();
	final AtomicLong taskIds = new AtomicLong();

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

	public void executeAsync(ExecutorService exec) {
		long id = taskIds.incrementAndGet();
		exec.execute(() -> { if (taskIds.get() == id) execute(); });
	}

	public void setText(String text) {
		command = parseCmdScript(text);
	}

	public void addParamChangeListener(Consumer<Void> l) {
		command.getParams().forEach(p -> p.changeEvent.addListener(l));
	}

}
