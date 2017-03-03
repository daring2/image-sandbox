package com.gitlab.daring.image.util;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class CommonUtils {

	public static <T> T tryRun(Callable<T> call) {
		try {
			return call.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void tryRun(VoidCallable call) {
		tryRun(() -> { call.call(); return null; });
	}

	public static void tryRun(VoidCallable call, Consumer<Exception> onError) {
		try {
			call.call();
		} catch (Exception e) {
			onError.accept(e);
		}
	}

	public static void closeQuietly(AutoCloseable c) {
		try {
			if (c != null) c.close();
		} catch (Exception e) {
			// ignore
		}
	}

	private CommonUtils() {
	}

}
