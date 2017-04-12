package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.indexer.FloatRawIndexer
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.*

internal class HoughCirclesCommand(args: Array<String>) : KBaseCommand(args) {

    val dp = intParam(1, "0-10")
    val minDist = intParam(100, "0-1000")
    val p1 = intParam(200, "0-1000")
    val p2 = intParam(100, "0-1000")
    val minRadius = intParam(0, "0-1000")
    val maxRadius = intParam(0, "0-1000")
    val limit = intParam(Int.MAX_VALUE, "0-100")

    val method = CV_HOUGH_GRADIENT

    override fun execute(env: CommandEnv) {
        val cs = Mat()
        HoughCircles(env.mat, cs, method, dp.dv, minDist.dv, p1.dv, p2.dv, minRadius.v, maxRadius.v)
        if (cs.empty()) return
        val ind = cs.createIndexer<FloatRawIndexer>()
        val size = Math.min(ind.cols(), limit.lv)
        for (i in 0..size - 1) {
            val cp = Point(ind.get(0, i, 0).toInt(), ind.get(0, i, 1).toInt())
            val radius = ind.get(0, i, 2).toInt()
            circle(env.mat, cp, radius, Scalar.WHITE)
        }
    }

}