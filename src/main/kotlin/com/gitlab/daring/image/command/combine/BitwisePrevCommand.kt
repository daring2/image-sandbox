package com.gitlab.daring.image.command.combine

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.opencv.empty
import org.bytedeco.javacpp.opencv_core.Mat

internal class BitwisePrevCommand(args: Array<String>) : KBaseCommand(args) {

    val op = enumParam(CombineMethod::class.java, CombineMethod.And)
    val histSize = nextArg(1).toInt()

    val hms = Array<Mat>(histSize + 1, { Mat() })
    var index = 0

    override fun execute(env: CommandEnv) {
        if (env.mat.empty()) return
        env.mat.copyTo(hms[index])
        for (i in hms.indices) {
            val hm = hms[i]
            if (i == index || hm.empty) continue
            op.v.func(env.mat, hm, env.mat)
        }
        index = (index + 1) % hms.size
    }

}