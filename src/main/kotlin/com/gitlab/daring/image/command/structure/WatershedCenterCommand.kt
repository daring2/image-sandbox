package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.command.parameter.IntParam
import com.gitlab.daring.image.opencv.size
import com.gitlab.daring.image.util.ImageUtils.smat
import org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.BLACK
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.watershed

internal class WatershedCenterCommand(args: Array<String>) : KBaseCommand(args) {

    val r1 = intParam(5, "0-100")
    val r2 = intParam(30, "0-100")
    val mt = enumParam(MarkerType.Circle)
    val segment = intParam(0, "0-2")
    val rm = Mat()

    override fun execute(env: CommandEnv) {
        val m = Mat(env.mat.size, CV_32SC1, BLACK)
        drawMarker(m, r1, 1)
        drawMarker(m, r2, 2)
        watershed(env.mat, m)
        max(m, 0.0).asMat().convertTo(rm, CV_8U)
        val si = smat(segment.v)
        inRange(rm, si, si, rm)
        env.mat = rm
    }

    fun drawMarker(m: Mat, p: IntParam, c: Int) {
        mt.v.drawCenter(m, p, c, if (c == 1) -1 else 1)
    }

}
