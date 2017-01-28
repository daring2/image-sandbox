package com.gitlab.daring.sandbox.image.util;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.split;

public class ExtStringUtils {

	public static List<String> splitAndTrim(String str, String sepChars) {
		return Arrays.stream(split(str, sepChars))
			.map(String::trim).filter(s -> !s.isEmpty())
			.collect(toList());
	}

	private ExtStringUtils() {
	}
}
