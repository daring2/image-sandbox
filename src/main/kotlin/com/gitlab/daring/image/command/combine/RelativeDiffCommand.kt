package com.gitlab.daring.image.command.combine

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_core.*

internal class RelativeDiffCommand(vararg args: String) : KBaseCommand(*args) {

    val mkey = stringParam("")

    val rm = Mat()
    val diffMat = Mat()

    override fun execute(env: CommandEnv) {
        val m1 = env.mat
        val m2 = env.getMat(mkey.v)
        absdiff(m1, m2, diffMat)
        val m3 = multiply(divide(diffMat, max(m1, m2)), 255.0)
        m3.asMat().convertTo(rm, CV_8U)
        env.mat = rm
    }

}