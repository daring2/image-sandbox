package com.gitlab.daring.image.command.drawing

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.util.OpencvConverters.toOpencv
import org.bytedeco.javacpp.opencv_core.LINE_8
import org.bytedeco.javacpp.opencv_core.Scalar.all
import org.bytedeco.javacpp.opencv_imgproc.CV_FILLED
import org.bytedeco.javacpp.opencv_imgproc.rectangle
import java.awt.Rectangle

class DrawVarCommand(vararg args: String): KBaseCommand(*args) {

    val key = stringParam("")
    val color = intParam(255, "0-255")
    val thickness = intParam(1, "0-10")

    override fun execute(env: CommandEnv) {
        val obj = env.getVar<Any>(key.v)
        val c = all(color.v.toDouble())
        val th = thickness.posVal(CV_FILLED)
        if (obj is Rectangle) {
            rectangle(env.mat, toOpencv(obj), c, th, LINE_8, 0)
        } else {
            throw IllegalArgumentException("object=" + obj)
        }
    }

}