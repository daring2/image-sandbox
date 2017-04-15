package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.parameter.IntParam
import com.gitlab.daring.image.opencv.rect
import com.gitlab.daring.image.opencv.sizeDim
import com.gitlab.daring.image.util.GeometryUtils.getCenterRect
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.circle
import org.bytedeco.javacpp.opencv_imgproc.rectangle

internal enum class MarkerType {

    Rectangle, Circle;

    fun drawCenter(m: Mat, p: IntParam, color: Int, th: Int) {
        val d = m.sizeDim
        val cr = getCenterRect(d, p.pv)
        val c = Scalar.all(color.toDouble())
        if (this == Rectangle) {
            rectangle(m, cr.rect, c, th, LINE_8, 0)
        } else if (this == Circle) {
            val cp = Point(d.width / 2, d.height / 2)
            val radius = Math.min(cr.width, cr.height) / 2
            circle(m, cp, radius, c, th, LINE_8, 0)
        } else {
            throw IllegalArgumentException("markerType=" + this)
        }
    }

}