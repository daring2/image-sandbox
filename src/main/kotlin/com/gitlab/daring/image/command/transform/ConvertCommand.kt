package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.command.transform.ConvertCommand.Target.Gray
import com.gitlab.daring.image.opencv.channels
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_imgproc.*

internal class ConvertCommand(vararg params: String) : KBaseCommand(*params) {

    val target = enumParam(Target.Gray)

    override fun execute(env: CommandEnv) {
        if (env.mat.channels == 1 != (target.v == Gray)) {
            val code = if (target.v == Gray) COLOR_BGR2GRAY else COLOR_GRAY2BGR
            env.mat = Mat().apply { cvtColor(env.mat, this, code) }
        }
    }

    enum class Target { Gray, Color }

}