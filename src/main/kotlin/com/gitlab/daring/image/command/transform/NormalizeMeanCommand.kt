package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_core.mean
import org.bytedeco.javacpp.opencv_core.multiply

internal class NormalizeMeanCommand(args: Array<String>) : KBaseCommand(args) {

    val meanValue = intParam(128, "0-255")

    override fun execute(env: CommandEnv) {
        val m1 = mean(env.mat)
        val f =  meanValue.v / m1.get()
        if (Math.abs(f - 1.0) > 0.001)
            env.mat = multiply(env.mat, f).asMat()
    }

}