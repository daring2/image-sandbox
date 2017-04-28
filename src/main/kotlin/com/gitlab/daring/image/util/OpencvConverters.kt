package com.gitlab.daring.image.util

import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_core.*
import java.awt.Dimension
import java.awt.Point
import java.awt.Rectangle

object OpencvConverters {

    fun toJava(p: opencv_core.Point) = Point(p.x(), p.y())

    fun toOpencv(p: Point) = opencv_core.Point(p.x, p.y)

    fun toJava(size: Size) = Dimension(size.width(), size.height())

    fun toOpencv(d: Dimension) = Size(d.width, d.height)

    fun toJava(rect: Rect): Rectangle {
        return Rectangle(rect.x(), rect.y(), rect.width(), rect.height())
    }

    fun newRect(p: opencv_core.Point, size: Size): Rectangle {
        return Rectangle(toJava(p), toJava(size))
    }

    fun toOpencv(r: Rectangle) = Rect(r.x, r.y, r.width, r.height)

    fun toJava(ms: MatVector) = (0..ms.size()).map(ms::get)

    fun toJava(ms: DMatchVector) = (0..ms.size()).map(ms::get)

}
