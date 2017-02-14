package com.gitlab.daring.image.command;

import com.gitlab.daring.image.event.ValueEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import static com.gitlab.daring.image.command.CommandScriptUtils.parseScript;
import static com.gitlab.daring.image.util.CommonUtils.tryRun;

public class CommandScript {

	public final CommandEnv env = new CommandEnv();
	final AtomicLong taskIds = new AtomicLong();
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

	public void execute() {
		CommandEnv.local.set(env);
		tryRun(() -> command.execute(env), errorEvent);
	}

	public void executeAsync(ExecutorService exec) {
		long id = taskIds.incrementAndGet();
		exec.execute(() -> { if (taskIds.get() == id) execute(); });
	}

	public void setText(String text) {
		tryRun(() -> command = parseScript(text), errorEvent);
	}

	public void addParamChangeListener(Consumer<Void> l) {
		command.getParams().forEach(p -> p.changeEvent.addListener(l));
	}

}
