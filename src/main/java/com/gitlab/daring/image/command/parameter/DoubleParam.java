package com.gitlab.daring.image.command.parameter;

import static java.lang.Double.parseDouble;
import static org.apache.commons.lang3.StringUtils.split;

public class DoubleParam extends CommandParam<Double> {

	public double minValue;
	public double maxValue;

	public DoubleParam(String sv, String sp) {
		super(sv, sp);
		parseSpec();
	}

	private void parseSpec() {
		String[] ss = split(spec, "-");
		minValue = parseDouble(ss[0]);
		maxValue = parseDouble(ss[1]);
	}

	@Override
	public Double parseValue(String sv) {
		return parseDouble(sv);
	}
	
}
