package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_features2d

internal class GFTTCommand(vararg args: String) : KBaseCommand(*args) {

    val maxFeatures = intParam(100, "0-100")
    val qualityLevel = doubleParam(1.0, "0-100")
    val minDistance = doubleParam(1.0, "0-20")
    val blockSize = intParam(3, "0-20")
    val harrisDetector = boolParam(false)
    val kp = intParam(4, "0-100")
    val detector = opencv_features2d.GFTTDetector.create()

    override fun execute(env: CommandEnv) {
        detector.maxFeatures = maxFeatures.v
        detector.qualityLevel = qualityLevel.pv()
        detector.minDistance = minDistance.v
        detector.blockSize = blockSize.v
        detector.harrisDetector = harrisDetector.v
        detector.k = kp.pv()
        env.featureDetector = detector
    }

}