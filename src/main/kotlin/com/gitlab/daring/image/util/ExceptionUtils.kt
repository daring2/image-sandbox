package com.gitlab.daring.image.util

object ExceptionUtils {

    @JvmStatic
    fun throwArgumentException(message: String) {
        throw IllegalArgumentException(message)
    }
}