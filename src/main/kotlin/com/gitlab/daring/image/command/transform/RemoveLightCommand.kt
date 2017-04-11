package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.opencv.height
import com.gitlab.daring.image.opencv.width
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.blur

internal class RemoveLightCommand(vararg params: String) : KBaseCommand(*params) {

    val blurRate = intParam(3, "0-255")

    val lightMap = Mat()
    val rm = Mat()

    override fun execute(env: CommandEnv) {
        val src = env.mat
        val br = blurRate.v
        blur(src, lightMap, Size(src.width / br, src.height / br))
        subtract(src, lightMap, rm)
        env.mat = rm
    }

}