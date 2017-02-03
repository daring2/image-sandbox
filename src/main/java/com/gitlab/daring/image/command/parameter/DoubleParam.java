package com.gitlab.daring.image.command.parameter;

import static java.lang.Double.parseDouble;

public class DoubleParam extends NumberParam<Double> {

	public DoubleParam(String sv, String sp) {
		super(sv, sp);
	}

	@Override
	public Double parseValue(String sv) {
		return parseDouble(sv);
	}

	@Override
	public void setNumValue(Number v) {
		setValue(v.doubleValue());
	}
	
}
