package com.gitlab.daring.image.util

import org.apache.commons.lang3.StringUtils.split

object ExtStringUtils {

    fun splitAndTrim(str: String, sepChars: String): List<String> {
        return split(str, sepChars).map(String::trim).filter(String::isNotEmpty)
    }

}