package com.gitlab.daring.image.util;

import java.util.concurrent.Callable;

public class CommonUtils {

	public static <T> T runQuietly(Callable<T> call) {
		try {
			return call.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void runQuietly(VoidCallable call) {
		runQuietly(() -> {call.call(); return "";});
	}

	public static void closeQuietly(AutoCloseable c) {
		if (c != null) runQuietly(c::close);
	}

	private CommonUtils() {
	}

}
