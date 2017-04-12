package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.util.ImageUtils.smat
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.*

internal class WatershedMarkerCommand(args: Array<String>) : KBaseCommand(args) {

    val markerKey = stringParam("")
    val segment = intParam(0, "0-10")

    val lm = Mat()
    val rm = Mat()

    override fun execute(env: CommandEnv) {
        val m = env.getMat(markerKey.v)
        threshold(m, m, 128.0, 255.0, THRESH_BINARY)
        connectedComponents(m, lm)
        watershed(env.mat, lm)
        max(lm, 0.0).asMat().convertTo(rm, CV_8U)
        val si = smat(segment.v)
        inRange(rm, si, si, rm)
        env.mat = rm
    }

}
