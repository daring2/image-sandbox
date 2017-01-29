package com.gitlab.daring.image.command;

@FunctionalInterface
public interface Command {

	void execute(CommandEnv env);

	@FunctionalInterface
	interface Factory {
		Command create(String[] params);
	}

}
