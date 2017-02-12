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

	public DoubleParam doubleParam(int i, double dv, String spec) {
		return addParam(new DoubleParam(arg(i, dv), spec));
	}

	public IntParam intParam(int i, int dv, String spec) {
		return addParam(new IntParam(arg(i, dv), spec));
	}

	public BooleanParam boolParam(int i, boolean dv) {
		return addParam(new BooleanParam(arg(i, dv)));
	}

	public String arg(int i, Object dv) {
		return i < args.length ? args[i] : "" + dv;
	}

	public <T extends Enum<T>> EnumParam<T> enumParam(Class<T> cl, int i, T dv) {
		return addParam(new EnumParam<>(cl, arg(i, dv)));
	}

	public <T extends Enum<T>> EnumParam<T> enumParam(Class<T> cl, int i) {
		return enumParam(cl, i, null);
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
