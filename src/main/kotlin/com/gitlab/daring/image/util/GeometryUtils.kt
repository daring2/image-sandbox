package com.gitlab.daring.image.util


import java.awt.Dimension
import java.awt.Point
import java.awt.Rectangle

object GeometryUtils {

    fun getCenterRect(r: Rectangle, rectSize: Double): Rectangle {
        if (rectSize == 1.0) return Rectangle(r)
        val p = getCenter(r)
        val sd = scale(r.size, rectSize)
        return Rectangle(p.x - sd.width / 2, p.y - sd.height / 2, sd.width, sd.height)
    }

    fun getCenterRect(d: Dimension, rectSize: Double): Rectangle {
        return getCenterRect(Rectangle(0, 0, d.width, d.height), rectSize)
    }

    fun getCenter(r: Rectangle): Point {
        return Point(r.x + r.width / 2, r.y + r.height / 2)
    }

    fun scale(d: Dimension, f: Double): Dimension {
        return Dimension(roundInt(d.width * f), roundInt(d.height * f))
    }

    fun scaleToMax(d: Dimension, md: Dimension): Dimension {
        if (md.width >= d.width && md.height >= d.height) return d
        val f = Math.min(1.0 * md.width / d.width, 1.0 * md.height / d.height)
        return scale(d, f)
    }

    fun roundInt(v: Double): Int {
        return Math.round(v).toInt()
    }

}
