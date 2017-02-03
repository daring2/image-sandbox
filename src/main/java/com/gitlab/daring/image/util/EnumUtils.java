package com.gitlab.daring.image.util;


import com.typesafe.config.Config;

public class EnumUtils {

	public static <T> T findEnum(T[] vs, String name) {
		String ln = name.toLowerCase();
		for (T v : vs) {
			String vn = v.toString().toLowerCase();
			if (vn.startsWith(ln)) return v;
		}
		throw new IllegalArgumentException("name=" + name);
	}

	public static <T extends Enum<T>> T findEnum(Class<T> cl, String name) {
		return findEnum(cl.getEnumConstants(), name);
	}

	public static <T extends Enum<T>> T findEnum(Config c, Class<T> cl, String path) {
		return findEnum(cl, c.getString(path));
	}

	private EnumUtils() {
	}
	
}
