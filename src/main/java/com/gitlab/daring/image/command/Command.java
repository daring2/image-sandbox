package com.gitlab.daring.image.command;

@FunctionalInterface
public interface Command extends AutoCloseable {

	void execute(CommandEnv env);

	default boolean isCacheable() {
		return true;
	}

	default void close() {}

	@FunctionalInterface
	interface Factory {
		Command create(String[] params);
	}

}
