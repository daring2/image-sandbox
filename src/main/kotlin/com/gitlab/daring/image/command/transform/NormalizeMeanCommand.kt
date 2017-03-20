package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_core.mean
import org.bytedeco.javacpp.opencv_core.multiply

class NormalizeMeanCommand(vararg args: String): KBaseCommand(*args) {

    val matKey = stringParam("")

    override fun execute(env: CommandEnv) {
        val m1 = mean(env.mat)
        val m2 = mean(env.getMat(matKey.v))
        val f =  m2.get() / m1.get()
        if (Math.abs(f - 1.0) > 0.001)
            env.mat = multiply(env.mat, f).asMat()
    }

}