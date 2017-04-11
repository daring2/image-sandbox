package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_xfeatures2d.StarDetector

internal class StarDetectorCommand(args: Array<String>) : KBaseCommand(args) {

    val maxSize = intParam(45, "0-100")
    val th1 = intParam(30, "0-100")
    val th2 = intParam(10, "0-100")
    val th3 = intParam(8, "0-100")
    val nonMaxSize = intParam(5, "0-100")

    override fun execute(env: CommandEnv) {
        env.featureDetector = StarDetector.create(maxSize.v, th1.v, th2.v, th3.v, nonMaxSize.v)
    }

}