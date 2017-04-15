package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.command.KernelParam
import org.bytedeco.javacpp.opencv_core.BORDER_CONSTANT
import org.bytedeco.javacpp.opencv_core.Point
import org.bytedeco.javacpp.opencv_imgproc.morphologyDefaultBorderValue
import org.bytedeco.javacpp.opencv_imgproc.morphologyEx

internal class MorphologyCommand(args: Array<String>) : KBaseCommand(args) {

    val operation = enumParam<Operation>(null)
    val iterCount = intParam(1, "0-10")
    val kernel = KernelParam(this)

    val anchor = Point(-1, -1)
    val borderType = BORDER_CONSTANT
    val borderValue = morphologyDefaultBorderValue()

    override fun execute(env: CommandEnv) {
        morphologyEx(env.mat, env.mat, operation.vi, kernel.v, anchor, iterCount.v, borderType, borderValue)
    }

    enum class Operation {
        Erode, Dilate, Open, Close, Gradient, TopHat, BlackHat, HitMiss
    }

}