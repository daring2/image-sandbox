package com.gitlab.daring.image.util

import com.gitlab.daring.image.util.CommonUtils.closeQuietly
import org.junit.Assert.assertEquals
import org.junit.Test

class CommonUtilsTest {

    @Test
    fun testCloseQuietly() {
        closeQuietly(null)
        closeQuietly(AutoCloseable { throw Exception("err1") })
        val closed = booleanArrayOf(false)
        closeQuietly(AutoCloseable { closed[0] = true })
        assertEquals(true, closed[0])
    }

}