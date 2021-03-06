package com.gitlab.daring.image.util


object EnumUtils {

    @JvmStatic
    fun <T> findEnum(vs: Array<T>, name: String): T {
        val ln = name.toLowerCase()
        for (v in vs) {
            if ("$v".toLowerCase().startsWith(ln)) return v
        }
        throw IllegalArgumentException("name=" + name)
    }

    @JvmStatic
    fun <T : Enum<T>> findEnum(cl: Class<T>, name: String): T {
        return findEnum(cl.enumConstants, name)
    }

}
