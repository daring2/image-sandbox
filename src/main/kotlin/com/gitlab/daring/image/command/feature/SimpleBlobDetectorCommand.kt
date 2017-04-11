package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_features2d.SimpleBlobDetector

internal class SimpleBlobDetectorCommand(args: Array<String>) : KBaseCommand(args) {

    //TODO support all params
    val minThreshold = intParam(0, "0-255")
    val maxThreshold = intParam(255, "0-255")
    val thresholdStep = intParam(5, "0-50")
    val minDistBetweenBlobs = intParam(10, "0-100")
    val minArea = intParam(10, "0-10000")
    val maxArea = intParam(10000, "0-10000")

    override fun execute(env: CommandEnv) {
        val ps = SimpleBlobDetector.Params()
        ps.minThreshold(minThreshold.fv)
        ps.maxThreshold(maxThreshold.fv)
        ps.thresholdStep(thresholdStep.fv)
        ps.minDistBetweenBlobs(minDistBetweenBlobs.fv)
        ps.minArea(minArea.fv)
        ps.maxArea(maxArea.fv)
        ps.filterByArea(minArea.v < maxArea.v)
        ps.filterByColor(false)
        ps.filterByCircularity(false)
        ps.filterByConvexity(false)
        env.featureDetector = SimpleBlobDetector.create(ps)
    }

}