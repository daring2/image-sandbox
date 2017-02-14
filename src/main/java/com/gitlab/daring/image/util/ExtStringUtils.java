package com.gitlab.daring.image.util;

import one.util.streamex.StreamEx;

import static org.apache.commons.lang3.StringUtils.split;

public class ExtStringUtils {

	public static StreamEx<String> splitAndTrim(String str, String sepChars) {
		return StreamEx.of(split(str, sepChars))
			.map(String::trim).remove(String::isEmpty);
	}

	private ExtStringUtils() {
	}
}
