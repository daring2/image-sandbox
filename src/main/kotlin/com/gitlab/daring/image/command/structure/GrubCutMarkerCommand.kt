package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.opencv.setTo
import com.gitlab.daring.image.util.ImageUtils.smat
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.*

internal class GrubCutMarkerCommand(args: Array<String>) : KBaseCommand(args) {

    val markerKey = stringParam("")
    val iterCount = intParam(5, "0-100")

    val cm = Mat()
    val rect = Rect()
    val bm = Mat()
    val fm = Mat()

    override fun execute(env: CommandEnv) {
        val m = env.getMat(markerKey.v)
        m.copyTo(cm)
        cm.setTo(0)
        cm.setTo(smat(GC_PR_BGD), greaterThan(m, 100.0).asMat())
        cm.setTo(smat(GC_FGD), greaterThan(m, 200.0).asMat())
        grabCut(env.mat, cm, rect, bm, fm, iterCount.v, GC_INIT_WITH_MASK)
        env.mat = multiply(and(cm, Scalar.all(1.0)), 255.0).asMat()
    }

}