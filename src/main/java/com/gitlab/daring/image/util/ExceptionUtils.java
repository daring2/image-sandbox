package com.gitlab.daring.image.util;

public class ExceptionUtils {

	public static void throwArgumentException(String message) {
		throw new IllegalArgumentException(message);
	}

	private ExceptionUtils() {
	}

}
