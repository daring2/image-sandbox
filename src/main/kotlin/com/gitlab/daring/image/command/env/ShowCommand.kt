package com.gitlab.daring.image.command.env

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.CommandScriptUtils.runCommand
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.swing.BaseCanvasFrame
import javax.swing.WindowConstants.DISPOSE_ON_CLOSE

internal class ShowCommand(args: Array<String>) : KBaseCommand(args) {

    val title = arg(0, "")
    val key = arg(1, "")
    val frame = BaseCanvasFrame(title)

    init {
        if (!isCacheable) frame.defaultCloseOperation = DISPOSE_ON_CLOSE
    }

    override val isCacheable get() = !title.isEmpty()

    override fun execute(env: CommandEnv) {
        val tv = env.eval(title)
        if (title == tv) {
            frame.showMat(env.getMat(key))
        } else {
            runCommand(env, "show", tv, key)
        }
    }

    override fun close() {
        frame.dispose()
    }

}