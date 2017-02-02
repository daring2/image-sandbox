package com.gitlab.daring.image.command;

import com.gitlab.daring.image.command.parameter.CommandParam;

import java.util.List;

import static java.util.Collections.emptyList;

@FunctionalInterface
public interface Command extends AutoCloseable {

	void execute(CommandEnv env);

	default List<CommandParam<?>> getParams() {
		return emptyList();
	}

	default boolean isCacheable() {
		return true;
	}

	default void close() {}

	@FunctionalInterface
	interface Factory {
		Command create(String[] params);
	}

}
