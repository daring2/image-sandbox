package com.gitlab.daring.image.command.combine

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_core.addWeighted

internal class AddWeightedCommand(vararg args: String) : KBaseCommand(*args) {

    val key = stringParam("")
    val f1 = doubleParam(50.0, "0-100")
    val f2 = doubleParam(50.0, "0-100")
    val f3 = doubleParam(0.0, "0-100")

    override fun execute(env: CommandEnv) {
        val m2 = env.getMat(key.v)
        addWeighted(env.mat, f1.pv(), m2, f2.pv(), f3.pv(), env.mat)
    }

}