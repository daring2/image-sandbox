package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_core.*

internal class NormalizeCommand(vararg args: String) : KBaseCommand(*args) {

    val alpha = intParam(0, "0-255")
    val beta = intParam(255, "0-255")
    val normType = enumParam(NormType.MinMax)
    val dtype = intParam(CV_8U, "0-10")

    val rm = Mat()
    val mask = Mat()

    override fun execute(env: CommandEnv) {
        val nt = normType.v.code
        normalize(env.mat, rm, alpha.dv, beta.dv, nt, dtype.v, mask)
        env.mat = rm
    }

    enum class NormType(val code: Int) {
        MinMax(32), Inf(1), L19(2), L2(4)
    }

}