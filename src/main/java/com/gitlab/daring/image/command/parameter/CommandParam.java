package com.gitlab.daring.image.command.parameter;

import com.gitlab.daring.image.event.VoidEvent;
import com.typesafe.config.Config;

import java.util.Objects;

import static com.gitlab.daring.image.util.ExtStringUtils.trimAll;
import static org.apache.commons.lang3.StringUtils.split;

public abstract class CommandParam<T> {

	public final VoidEvent changeEvent = new VoidEvent();

	protected final String[] args;
	public final String name;
	public final String spec;
	public String configPath;

	public volatile T v;

	public CommandParam(String sv, String sp) {
		args = trimAll(split(" " + sv, ":"));
		name = arg(1, "");
		spec = arg(2, sp);
		setStringValue(arg(0, ""));
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

	public void setStringValue(String v) {
		setValue(parseValue(v));
	}

	public <P extends CommandParam<T>> P bind(Config c, String path) {
		configPath = path;
		setStringValue(c.getString(path));
		return (P) this;
	}

	public Object configValue() {
		return v;
	}

}
