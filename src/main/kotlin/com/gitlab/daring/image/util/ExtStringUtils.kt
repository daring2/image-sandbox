package com.gitlab.daring.image.util

import one.util.streamex.StreamEx
import org.apache.commons.lang3.StringUtils.split

object ExtStringUtils {

    @JvmStatic
    fun splitAndTrim(str: String, sepChars: String): StreamEx<String> {
        return StreamEx.of(*split(str, sepChars)).map(String::trim).remove(String::isEmpty);
    }

    @JvmStatic
    fun trimAll(ss: Array<String>): Array<String> {
        for (i in ss.indices) ss[i] = ss[i].trim()
        return ss
    }

}