package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.opencv.toList
import org.bytedeco.javacpp.opencv_core.MatVector
import org.bytedeco.javacpp.opencv_imgproc.findContours

internal class FindContoursCommand(params: Array<String>) : KBaseCommand(params) {

    val mode = enumParam(Mode.List)
    val method = enumParam(ApproxMethod.None)

    override fun execute(env: CommandEnv) {
        val mv = MatVector()
        findContours(env.mat.clone(), mv, mode.vi(), method.vi() + 1)
        env.contours = mv.toList().map(::Contour)
    }

    enum class Mode {
        External, List, CComp, Tree, FloodFill
    }

    enum class ApproxMethod {
        None, Simple, TC89_L1, TC89_KCOS
    }

}
