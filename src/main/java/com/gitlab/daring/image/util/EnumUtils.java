package com.gitlab.daring.image.util;


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

    private EnumUtils() {
    }

}
