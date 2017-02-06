package com.gitlab.daring.image.command.parameter;

import static java.lang.Boolean.parseBoolean;

public class BooleanParam extends CommandParam<Boolean> {

	public BooleanParam(String sv) {
		super(sv, "");
	}

	@Override
	public Boolean parseValue(String sv) {
		return parseBoolean(sv);
	}

}
