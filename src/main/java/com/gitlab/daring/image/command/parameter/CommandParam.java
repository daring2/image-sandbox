package com.gitlab.daring.image.command.parameter;

import com.gitlab.daring.image.event.VoidEvent;

import static org.apache.commons.lang3.StringUtils.split;

public abstract class CommandParam<T> {

	protected final String[] args;
	public final String name;
	public final String spec;
	public final VoidEvent changeEvent = new VoidEvent();
	
	public T v;

	public CommandParam(String sv, String sp) {
		args = split(sv, ":");
		v = parseValue(args[0]);
		name = args.length > 1 ? args[1] : "";
		spec = args.length > 2 ? args[2] : sp;
	}

	public abstract T parseValue(String sv);

	public T getValue() {
		return v;
	}

	public void setValue(T v) {
		this.v = v;
		changeEvent.fire();
	}

}
