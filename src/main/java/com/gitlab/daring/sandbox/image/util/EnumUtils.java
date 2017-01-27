package com.gitlab.daring.sandbox.image.util;

public class EnumUtils {

	public static <T extends Enum<T>> T getEnum(Class<T> cl, String name) {
		return Enum.valueOf(cl, name.toUpperCase());
	}

	public static int getEnumIndex(Class<? extends Enum> cl, String name) {
		return getEnum(cl, name).ordinal();
	}

	private EnumUtils() {
	}
	
}
