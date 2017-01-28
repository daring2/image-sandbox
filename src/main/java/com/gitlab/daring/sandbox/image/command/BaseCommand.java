package com.gitlab.daring.sandbox.image.command;

import java.util.Arrays;

public abstract class BaseCommand implements Command {

	protected final String[] args;

	public BaseCommand(String... args) {
		this.args = args;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BaseCommand that = (BaseCommand) o;
		return Arrays.equals(args, that.args);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(args);
	}

}
