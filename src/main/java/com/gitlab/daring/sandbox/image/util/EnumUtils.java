package com.gitlab.daring.sandbox.image.util;


public class EnumUtils {

	public static <T> T findEnum(T[] vs, String name) {
		String ln = name.toLowerCase();
		for (T v : vs) {
			String vn = v.toString().toLowerCase();
			if (vn.startsWith(ln)) return v;
		}
		throw new IllegalArgumentException("name=" + name);
	}

	public static int findEnumIndex(Enum[] vs, String name) {
		return findEnum(vs, name).ordinal();
	}

	private EnumUtils() {
	}
	
}
