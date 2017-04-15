package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.opencv.height
import com.gitlab.daring.image.opencv.width
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_core.Size
import org.bytedeco.javacpp.opencv_imgproc.resize

internal class ScaleToSizeCommand(args: Array<String>) : KBaseCommand(args) {

    val matSize = intParam(0, "0-5000")
    val maxFactor = intParam(95, "0-100")
    val method = enumParam(InterMethod.Linear)

    val rm = Mat()
    val s0 = Size()

    override fun execute(env: CommandEnv) {
        val m = env.mat
        val f = 1.0 * matSize.v / Math.max(m.height, m.width)
        if (f > maxFactor.pv) return
        resize(m, rm, s0, f, f, method.vi)
        env.mat = rm
    }

}