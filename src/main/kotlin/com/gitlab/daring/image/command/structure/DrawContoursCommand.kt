package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.util.ImageUtils.newScalar
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.CV_FILLED
import org.bytedeco.javacpp.opencv_imgproc.drawContours

internal class DrawContoursCommand(args: Array<String>) : KBaseCommand(args) {

    val index = intParam(0, "0-10")
    val color = stringParam("#FFFFFF")
    val thickness = intParam(1, "0-10")
    val maxLevel = Integer.MAX_VALUE

    val h = Mat()
    val offset = Point()

    override fun execute(env: CommandEnv) {
        if (index.v > env.contours.size) return
        val cs = MatVector(*env.contours.map { it.mat }.toTypedArray())
        val th = thickness.posVal(CV_FILLED)
        drawContours(env.mat, cs, index.v - 1, newScalar(color.v), th, LINE_8, h, maxLevel, offset)
    }

}