package com.gitlab.daring.image.command.parameter;

import static java.lang.Integer.parseInt;

public class IntParam extends NumberParam<Integer> {

	public IntParam(String sv, String sp) {
		super(sv, sp);
	}

	@Override
	public Integer parseValue(String sv) {
		return parseInt(sv);
	}

	@Override
	public void setNumValue(Number v) {
		setValue(v.intValue());
	}

}
