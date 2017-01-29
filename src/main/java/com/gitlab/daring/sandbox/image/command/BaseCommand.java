package com.gitlab.daring.sandbox.image.command;

import java.util.Arrays;

public abstract class BaseCommand implements Command {

	protected final String[] params;

	public BaseCommand(String... params) {
		this.params = params;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BaseCommand that = (BaseCommand) o;
		return Arrays.equals(params, that.params);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(params);
	}

	@Override
	public String toString() {
		return "BaseCommand{" +
			"params=" + Arrays.toString(params) +
			'}';
	}

}
