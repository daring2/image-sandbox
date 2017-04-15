package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.opencv.setTo

internal class ShowContoursCommand(args: Array<String>) : KBaseCommand(args) {

    val findCmd = FindContoursCommand(emptyArray())
    val filterCmd = FilterContoursCommand(args)
    val drawCmd = DrawContoursCommand(emptyArray())

    init {
        params.addAll(filterCmd.params)
    }

    override fun execute(env: CommandEnv) {
        findCmd.execute(env)
        filterCmd.execute(env)
        env.mat.setTo(0)
        drawCmd.execute(env)
    }

}