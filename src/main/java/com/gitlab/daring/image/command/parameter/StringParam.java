package com.gitlab.daring.image.command.parameter;

public class StringParam extends CommandParam<String> {

	public StringParam(String sv) {
		super(sv, "");
	}

	@Override
	public String parseValue(String sv) {
		return sv;
	}

}