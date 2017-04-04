package com.gitlab.daring.image.config

import com.gitlab.daring.image.config.ConfigUtils.configFromString
import com.gitlab.daring.image.config.ConfigUtils.emptyConfig
import org.junit.Assert.assertEquals
import org.junit.Test

class ConfigDiffBuilderTest {

    @Test
    fun testBuild() {
        val b = ConfigDiffBuilder()
        val ec = emptyConfig()
        val c1 = configFromString("p1 = v1, p2 = { p3 = v3}")
        assertEquals(c1, b.build(c1, ec))
        assertEquals(ec, b.build(c1, c1))
        val c2 = configFromString("p1 = cv1, p2 = { p3 = v3}")
        assertEquals(configFromString("p1 = v1"), b.build(c1, c2))
        val c3 = configFromString("p1 = v1, p2 = { p3 = cv3}")
        assertEquals(configFromString("p2.p3 = v3"), b.build(c1, c3))
        val c4 = configFromString("p1 = v1, p2 = { p3 = v3}, p4 = v4")
        assertEquals(ec, b.build(c1, c4))
    }

}