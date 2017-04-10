package com.gitlab.daring.image.command.env

import com.gitlab.daring.image.command.Command
import com.gitlab.daring.image.command.CommandRegistry
import com.gitlab.daring.image.command.SimpleCommand

import org.bytedeco.javacpp.opencv_imgcodecs.imwrite

object EnvCommands {

    @JvmStatic
    fun register(r: CommandRegistry) {
        r.register("write", this::newWriteCommand)
        r.register("get", this::newGetCommand)
        r.register("put", this::newPutCommand)
        r.register("read", ::ReadCommand)
        r.register("show", ::ShowCommand)
        r.register("task", ::SetTaskCommand)
    }

    fun newWriteCommand(vararg ps: String): Command {
        val c = SimpleCommand(*ps)
        val file = c.arg(0, "")
        val key = c.arg(1, "")
        return Command { env -> imwrite(env.eval(file), env.getMat(key)) }
    }

    fun newGetCommand(vararg ps: String): Command {
        return Command { env -> env.getMat(ps[0]).copyTo(env.mat) }
    }

    fun newPutCommand(vararg ps: String): Command {
        return Command { env -> env.putMat(ps[0], env.mat) }
    }

}
