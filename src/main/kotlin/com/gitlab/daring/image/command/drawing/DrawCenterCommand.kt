package com.gitlab.daring.image.command.drawing

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.opencv.rect
import com.gitlab.daring.image.opencv.sizeDim
import com.gitlab.daring.image.util.GeometryUtils.getCenterRect
import org.bytedeco.javacpp.opencv_core.LINE_8
import org.bytedeco.javacpp.opencv_core.Point
import org.bytedeco.javacpp.opencv_core.Scalar.all
import org.bytedeco.javacpp.opencv_imgproc.*

internal class DrawCenterCommand(args: Array<String>) : KBaseCommand(args) {

    val shape = enumParam(Shape.Rectangle)
    val scale = intParam(10, "0-100")
    val color = intParam(255, "0-255")
    val thickness = intParam(1, "0-10")

    override fun execute(env: CommandEnv) {
        val m = env.mat
        val d = m.sizeDim
        val cr = getCenterRect(d, scale.pv)
        val c = all(color.dv)
        val th = thickness.posVal(CV_FILLED)
        val sh = shape.v
        if (sh == Shape.Rectangle) {
            rectangle(m, cr.rect, c, th, LINE_8, 0)
        } else if (sh == Shape.Circle) {
            val cp = Point(d.width / 2, d.height / 2)
            circle(m, cp, cr.width / 2, c, th, LINE_8, 0)
        } else {
            throw IllegalArgumentException("shape=" + sh)
        }
    }

    enum class Shape {Rectangle, Circle}

}