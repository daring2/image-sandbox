package com.gitlab.daring.image.command.parameter;

import static java.lang.Integer.parseInt;

public class FileParam extends StringParam {

	public int maxCount;

	public FileParam(String sv) {
		super(sv);
		parseSpec();
	}

	private void parseSpec() {
		if (spec.isEmpty()) return;
		maxCount = parseInt(spec);
	}

}