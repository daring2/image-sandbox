package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.features.FeatureUtils.buildMatchPoints
import org.bytedeco.javacpp.opencv_calib3d.findHomography
import org.bytedeco.javacpp.opencv_core.Mat

class FindHomographyCommand(vararg args: String): KBaseCommand(*args) {

    val method = enumParam<HomographyMethod>(HomographyMethod.RANSAC)
    val reprojThreshold = doubleParam(3.0, "0-10")
    val maxIters = intParam(2000, "0-10000")
    val confidence = doubleParam(0.995, "0-1")

    val mask = Mat()

    override fun execute(env: CommandEnv) {
        val (ps1, ps2, ms) = env.matchResult
        env.mat = findHomography(
                buildMatchPoints(ms, ps1.points, true),
                buildMatchPoints(ms, ps2.points, false),
                method.v.code, reprojThreshold.v, mask, maxIters.v, confidence.v
        )
    }

}