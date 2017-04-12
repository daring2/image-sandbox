package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_core.TermCriteria
import org.bytedeco.javacpp.opencv_imgproc.pyrMeanShiftFiltering

internal class PyrMeanShiftFilterCommand(args: Array<String>) : KBaseCommand(args) {

    val sp = intParam(10, "0-100")
    val sr = intParam(10, "0-100")
    val ml = intParam(1, "0-5")
    val tr = TermCriteria(3, 5, 1.0)
    val rm = Mat()

    override fun execute(env: CommandEnv) {
        pyrMeanShiftFiltering(env.mat, rm, sp.dv, sr.dv, ml.v, tr)
        env.mat = rm
    }

}
