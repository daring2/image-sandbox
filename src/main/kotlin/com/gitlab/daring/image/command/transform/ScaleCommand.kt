package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.command.transform.InterMethod.Linear
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_core.Size
import org.bytedeco.javacpp.opencv_imgproc.resize

internal class ScaleCommand(args: Array<String>) : KBaseCommand(args) {

    val factor = intParam(100, "0-200")
    val method = enumParam(Linear)

    val rm = Mat()
    val s0 = Size()

    override fun execute(env: CommandEnv) {
        if (factor.v == 100) return
        val f = Math.max(factor.pv, 0.005)
        resize(env.mat, rm, s0, f, f, method.vi)
        env.mat = rm
    }

}