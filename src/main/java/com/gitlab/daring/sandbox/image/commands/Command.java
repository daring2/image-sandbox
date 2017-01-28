package com.gitlab.daring.sandbox.image.commands;

@FunctionalInterface
public interface Command {

	void execute(CommandEnv env);

	@FunctionalInterface
	interface Factory {
		Command create(String[] args);
	}

}
