package com.gitlab.daring.image.util

import com.gitlab.daring.image.util.EnumUtils.findEnum
import com.gitlab.daring.image.util.EnumUtilsTest.TestEnum.*
import org.junit.Assert.assertEquals
import org.junit.Test

class EnumUtilsTest {

    @Test
    fun testFindEnum() {
        checkFindEnum(One, "One")
        checkFindEnum(One, "one")
        checkFindEnum(One, "o")
        checkFindEnum(Two, "two")
        checkFindEnum(Two, "t")
        checkFindEnum(Three, "th")
    }

    fun checkFindEnum(exp: TestEnum, name: String) {
        assertEquals(exp, findEnum(TestEnum::class.java, name))
    }

    enum class TestEnum { One, Two, Three }

}