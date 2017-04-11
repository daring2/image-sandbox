package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.util.ImageUtils.smat
import org.bytedeco.javacpp.opencv_core.*

internal class KeepRangeCommand(args: Array<String>) : KBaseCommand(args) {

    val lowerb = intParam(0, "0-255")
    val upperb = intParam(255, "0-255")

    val rm = Mat()

    override fun execute(env: CommandEnv) {
        val m = env.mat
        inRange(m, smat(lowerb.v), smat(upperb.v), rm)
        min(m, rm, rm)
    }
    
}

