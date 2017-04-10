package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_features2d.ORB

internal class ORBCommand(vararg args: String) : KBaseCommand(*args) {

    //TODO support all params
    val maxFeatures = intParam(100, "0-100")
    val scale = doubleParam(12.0, "0-100")
    val nlevels = intParam(8, "0-100")
    val detector = ORB.create()

    override fun execute(env: CommandEnv) {
        detector.maxFeatures = maxFeatures.v
        detector.scaleFactor = scale.v * 0.1
        detector.nLevels = nlevels.v
        env.featureDetector = detector
    }

}