package com.gitlab.daring.image.command.parameter;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.split;

public class IntParam extends CommandParam<Integer> {

	public int minValue;
	public int maxValue;

	public IntParam(String sv, String sp) {
		super(sv, sp);
		parseSpec();
	}

	private void parseSpec() {
		String[] ss = split(spec, "-");
		minValue = parseInt(ss[0]);
		maxValue = parseInt(ss[1]);
	}

	@Override
	public Integer parseValue(String sv) {
		return parseInt(sv);
	}
	
}
