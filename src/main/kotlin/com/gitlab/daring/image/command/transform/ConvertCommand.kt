package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.command.transform.ConvertCommand.Target.Gray
import com.gitlab.daring.image.opencv.gray
import com.gitlab.daring.image.opencv.size
import com.gitlab.daring.image.opencv.type
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.*

internal class ConvertCommand(vararg params: String) : KBaseCommand(*params) {

    val target = enumParam(Gray)
    val channel = enumParam(Channel.None)
    val rm = Mat()

    override fun execute(env: CommandEnv) {
        if (env.mat.gray == (target.v == Gray)) return
        val m = env.mat
        val c = channel.v
        if (target.v == Gray) {
            cvtColor(m, rm, COLOR_BGR2GRAY)
        } else if (c == Channel.None) {
            cvtColor(m, rm, COLOR_GRAY2BGR)
        } else {
            val cms = Array(3, { Mat(m.size, m.type, Scalar.BLACK)})
            m.copyTo(cms[c.ordinal])
            merge(MatVector(*cms), rm)
        }
        env.mat = rm
    }

    enum class Target { Gray, Color }
    enum class Channel { Green, Blue, Red, None }

}