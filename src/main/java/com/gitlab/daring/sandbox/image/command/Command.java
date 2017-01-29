package com.gitlab.daring.sandbox.image.command;

@FunctionalInterface
public interface Command {

	void execute(CommandEnv env);

	@FunctionalInterface
	interface Factory {
		Command create(String[] params);
	}

}
