package com.gitlab.daring.image.command.parameter;

import static org.apache.commons.lang3.StringUtils.split;

public abstract class CommandParam<T> {

	public T v;
	public String name;
	public String spec;

	public CommandParam(String sv, String sp) {
		String[] ss = split(sv, ":");
		v = parseValue(ss[0]);
		name = ss.length > 1 ? ss[1] : "";
		spec = ss.length > 2 ? ss[2] : sp;
	}

	public abstract T parseValue(String sv);

	public T getValue() {
		return v;
	}

	public void setValue(T v) {
		this.v = v;
	}

}
