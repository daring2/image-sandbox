package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.Laplacian

internal class LaplacianCommand(args: Array<String>) : KBaseCommand(args) {

    val ksize = intParam(1, "1-10")
    val rm = Mat()

    override fun execute(env: CommandEnv) {
        val kf = ksize.v * 2 + 1
        Laplacian(env.mat, rm, CV_64F, kf, 1.0, 0.0, BORDER_DEFAULT)
        convertScaleAbs(rm, env.mat)
    }

}