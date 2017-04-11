package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.command.transform.InterMethod.Linear
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.warpPerspective

internal class WarpPerspectiveCommand(args: Array<String>) : KBaseCommand(args) {

    val method = enumParam(Linear)
    val rm = Mat()

    override fun execute(env: CommandEnv) {
        val (points1) = env.matchResult
        val m1 = points1.mat
        warpPerspective(m1, rm, env.mat, m1.size(), method.vi(), BORDER_CONSTANT, Scalar.BLACK)
        env.mat = rm
    }

}