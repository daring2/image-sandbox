package com.gitlab.daring.image.util

object CommonUtils {

    @JvmStatic
    fun closeQuietly(c: AutoCloseable?) {
        try {
            c?.close()
        } catch (e: Exception) {
            // ignore
        }

    }

}