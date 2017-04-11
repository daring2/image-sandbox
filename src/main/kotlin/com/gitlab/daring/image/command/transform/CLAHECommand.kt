package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_core.Size
import org.bytedeco.javacpp.opencv_imgproc.createCLAHE

internal class CLAHECommand(args: Array<String>) : KBaseCommand(args) {

    val clipLimit = doubleParam(40.0, "0-255")
    val tilesGridSize = intParam(8, "0-255")

    val alg = createCLAHE()
    val rm = Mat()

    override fun execute(env: CommandEnv) {
        alg.clipLimit = clipLimit.v
        tilesGridSize.v.let { alg.tilesGridSize = Size(it, it) }
        alg.apply(env.mat, rm)
        env.mat = rm
    }

}