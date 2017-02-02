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

	public static <T> T findEnum(Class<T> cl, String name) {
		return findEnum(cl.getEnumConstants(), name);
	}

	public static <T> T findEnum(Config c, String path, T[] vs) {
		return findEnum(vs, c.getString(path));
	}

	public static int findEnumIndex(Enum[] vs, String name) {
		return findEnum(vs, name).ordinal();
	}

	private EnumUtils() {
	}
	
}
