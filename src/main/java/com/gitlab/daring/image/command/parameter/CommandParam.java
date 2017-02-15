package com.gitlab.daring.image.command.parameter;

import com.gitlab.daring.image.event.VoidEvent;

import java.util.Objects;

import static com.gitlab.daring.image.util.ExtStringUtils.trimAll;
import static org.apache.commons.lang3.StringUtils.split;

public abstract class CommandParam<T> {

	protected final String[] args;
	public final String name;
	public final String spec;
	public final VoidEvent changeEvent = new VoidEvent();
	
	public volatile T v;

	public CommandParam(String sv, String sp) {
		args = trimAll(split(" " + sv, ":"));
		name = arg(1, "");
		spec = arg(2, sp);
		setValue(parseValue(arg(0, "")));
	}

	public String arg(int i, String dv) {
		return args.length > i ? args[i] : dv;
	}

	public abstract T parseValue(String sv);

	public T getValue() {
		return v;
	}

	public void setValue(T v) {
		if (Objects.equals(this.v, v)) return;
		this.v = v;
		changeEvent.fire();
	}

}
