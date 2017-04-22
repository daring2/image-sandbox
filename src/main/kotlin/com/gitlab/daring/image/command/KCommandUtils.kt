package com.gitlab.daring.image.command

object KCommandUtils {

    fun newCommand(f: (CommandEnv) -> Unit): Command {
        return SimpleCommand(emptyArray()).apply { func = f }
    }

}
