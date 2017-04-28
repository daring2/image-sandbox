package com.gitlab.daring.image.command

import com.gitlab.daring.image.command.CommandUtils.parseArgs
import com.gitlab.daring.image.command.CommandUtils.splitScript
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Collections.emptyList

class CommandUtilsTest {

    @Test
    fun testSplitScript() {
        assertEquals(emptyList<String>(), splitScript(""))
        assertEquals(listOf("c1", "c2", "c3"), splitScript("c1; c2\n c3"))
        assertEquals(listOf("c1", "c3", "c6"), splitScript("c1; -c2; c3\n// c4, c5\nc6"))
    }

    @Test
    fun testParseArgs() {
        assertArrayEquals(emptyArray<String>(), parseArgs(""))
        assertArrayEquals(arrayOf("p1", "p2", "p3"), parseArgs("p1,, p2, , p3 "))
    }

}