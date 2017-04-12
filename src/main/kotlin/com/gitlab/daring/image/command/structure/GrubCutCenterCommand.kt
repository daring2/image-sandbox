package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.opencv.size
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.*

internal class GrubCutCenterCommand(args: Array<String>) : KBaseCommand(args) {

    val r1 = intParam(5, "0-100")
    val r2 = intParam(90, "0-100")
    val mt = enumParam(MarkerType.Circle)
    val iterCount = intParam(5, "0-100")

    val rect = Rect()
    val bm = Mat()
    val fm = Mat()

    override fun execute(env: CommandEnv) {
        val m = Mat(env.mat.size, CV_8U, Scalar.BLACK)
        mt.v.drawCenter(m, r2, GC_PR_FGD, CV_FILLED)
        mt.v.drawCenter(m, r1, GC_FGD, CV_FILLED)
        //		env.mat = multiply(m, 128).asMat();
        grabCut(env.mat, m, rect, bm, fm, iterCount.v, GC_INIT_WITH_MASK)
        env.mat = multiply(and(m, Scalar.all(1.0)), 255.0).asMat()
    }

}
