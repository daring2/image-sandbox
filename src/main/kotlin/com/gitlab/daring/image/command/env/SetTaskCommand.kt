package com.gitlab.daring.image.command.env

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand

internal class SetTaskCommand(vararg args: String) : KBaseCommand(*args) {

    override fun isEnabled(env: CommandEnv) = true

    override fun execute(env: CommandEnv) {
        env.curTask = arg(0, "")
    }

}