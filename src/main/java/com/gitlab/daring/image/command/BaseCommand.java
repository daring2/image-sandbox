package com.gitlab.daring.image.command;

import com.gitlab.daring.image.command.parameter.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseCommand implements Command {

	protected final String[] args;
	protected final List<CommandParam<?>> params = new ArrayList<>();

	public BaseCommand(String... args) {
		this.args = args;
	}

	@Override
	public List<CommandParam<?>> getParams() {
		return params;
	}

	public <T extends CommandParam> T addParam(T p) {
		params.add(p);
		return p;
	}

	public DoubleParam doubleParam(int index, String spec) {
		return addParam(new DoubleParam(args[index], spec));
	}

	public IntParam intParam(int index, String spec) {
		return addParam(new IntParam(args[index], spec));
	}

	public BooleanParam boolParam(int index) {
		return addParam(new BooleanParam(args[index]));
	}

	public <T extends Enum<T>> EnumParam<T> enumParam(Class<T> cl, int index) {
		return addParam(new EnumParam<>(cl, args[index]));
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

	@Override
	public String toString() {
		return "BaseCommand{" +
			"params=" + Arrays.toString(args) +
			'}';
	}

}
