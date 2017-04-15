package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.opencv.toJava
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_imgproc.*
import java.lang.Math.pow
import java.lang.Math.sqrt
import java.util.Comparator.comparingDouble

enum class ContourMetric {

    Length, Area, Size, MinSize, Diameter;

    var comparator = comparingDouble<Contour> { it.getMetric(this) }

    fun calculate(m: Mat): Double {
        val r = boundingRect(m).toJava()
        if (this == Length) {
            return arcLength(m, false)
        } else if (this == Area) {
            return contourArea(m)
        } else if (this == Size) {
            return Math.max(r.width, r.height).toDouble()
        } else if (this == MinSize) {
            return Math.min(r.width, r.height).toDouble()
        } else if (this == Diameter) {
            return sqrt(pow(r.getWidth(), 2.0) + pow(r.getHeight(), 2.0))
        } else {
            throw IllegalArgumentException("metric=" + m)
        }
    }

}