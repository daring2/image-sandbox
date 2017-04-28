package com.gitlab.daring.image.util

import com.gitlab.daring.image.util.GeometryUtils.getCenterRect
import com.gitlab.daring.image.util.GeometryUtils.scale
import com.gitlab.daring.image.util.GeometryUtils.scaleToMax
import org.junit.Assert.assertEquals
import org.junit.Test
import java.awt.Dimension
import java.awt.Rectangle

class GeometryUtilsTest {

    @Test
    fun testCenterRect() {
        val r1 = Rectangle(10, 5, 40, 20)
        assertEquals(r1, getCenterRect(r1, 1.0))
        assertEquals(Rectangle(25, 13, 10, 5), getCenterRect(r1, 0.25))
    }

    @Test
    fun testScale() {
        val d1 = Dimension(40, 20)
        assertEquals(d1, scale(d1, 1.0))
        assertEquals(Dimension(60, 30), scale(d1, 1.5))
        assertEquals(Dimension(10, 5), scale(d1, 0.25))
        assertEquals(Dimension(4, 2), scale(d1, 0.1))
    }

    @Test
    fun testScaleToMax() {
        val d1 = Dimension(20, 10)
        assertEquals(d1, scaleToMax(d1, d1))
        assertEquals(d1, scaleToMax(d1, Dimension(40, 20)))
        assertEquals(Dimension(10, 5), scaleToMax(d1, Dimension(10, 20)))
        assertEquals(Dimension(10, 5), scaleToMax(d1, Dimension(40, 5)))
    }

}