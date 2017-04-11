package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_xfeatures2d.SURF

internal class SURFCommand(args: Array<String>) : KBaseCommand(args) {

    val hessianThreshold = intParam(100, "20-200")
    val nOctaves = intParam(4, "0-10")
    val nOctaveLayers = intParam(3, "0-10")

    val detector = SURF.create()

    override fun execute(env: CommandEnv) {
        detector.hessianThreshold = hessianThreshold.dv
        detector.nOctaves = nOctaves.v
        detector.nOctaveLayers = nOctaveLayers.v
        env.featureDetector = detector
    }

}