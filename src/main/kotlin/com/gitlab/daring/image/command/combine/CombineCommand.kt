package com.gitlab.daring.image.command.combine

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.opencv.gray
import com.gitlab.daring.image.opencv.toColored

internal class CombineCommand(args: Array<String>) : KBaseCommand(args) {

    val matKey = stringParam("")
    val method = enumParam<CombineMethod>(null)

    override fun execute(env: CommandEnv) {
        var m1 = env.mat
        var m2 = env.getMat(matKey.v)
        if (m1.gray != m2.gray) {
            m1 = m1.toColored()
            m2 = m2.toColored()
        }
        method.v.func(m1, m2, m1)
        env.mat = m1
    }
}