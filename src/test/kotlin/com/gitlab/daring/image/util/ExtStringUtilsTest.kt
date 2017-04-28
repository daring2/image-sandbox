package com.gitlab.daring.image.util

import com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim
import org.junit.Assert.assertEquals
import org.junit.Test

class ExtStringUtilsTest {

    @Test
    fun testSplitAndTrim() {
        checkSplitAndTrim("p1, p2, p3", ", ", "p1", "p2", "p3")
        checkSplitAndTrim(" p1 , , p2 ", ",", "p1", "p2")
        checkSplitAndTrim("p1;p2", ";", "p1", "p2")
    }

    internal fun checkSplitAndTrim(str: String, sep: String, vararg ss: String) {
        assertEquals(listOf(*ss), splitAndTrim(str, sep))
    }

}