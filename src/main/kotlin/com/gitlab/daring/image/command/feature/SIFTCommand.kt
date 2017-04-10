package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_xfeatures2d.SIFT

internal class SIFTCommand(vararg args: String) : KBaseCommand(*args) {

    val maxFeatures = intParam(100, "0-100")
    val nOctaveLayers = intParam(3, "0-10")
    val contrastThreshold = doubleParam(4.0, "0-100")
    val edgeThreshold = doubleParam(10.0, "0-100")
    val sigma = doubleParam(1.6, "0.1-10")

    override fun execute(env: CommandEnv) {
        env.featureDetector = SIFT.create(
            maxFeatures.v, nOctaveLayers.v, contrastThreshold.pv, edgeThreshold.v, sigma.v
        )
    }

}
