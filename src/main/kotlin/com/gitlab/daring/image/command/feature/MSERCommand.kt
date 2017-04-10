package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_features2d.MSER

internal class MSERCommand(vararg args: String) : KBaseCommand(*args) {

    //TODO support all params
    val delta = intParam(5, "0-100")
    val minArea = intParam(60, "0-1000")
    val maxArea = intParam(14400, "0-100000")
    val detector = MSER.create()

    override fun execute(env: CommandEnv) {
        detector.delta = delta.v
        detector.minArea = minArea.v
        detector.maxArea = maxArea.v
        env.featureDetector = detector
    }

}
