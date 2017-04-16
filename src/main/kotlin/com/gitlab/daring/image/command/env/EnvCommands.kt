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

    fun newWriteCommand(args: Array<String>): Command {
        val c = SimpleCommand(args)
        val file = c.arg(0, "")
        val key = c.arg(1, "")
        return Command { env -> imwrite(env.eval(file), env.getMat(key)) }
    }

    fun newGetCommand(args: Array<String>): Command {
        return Command { env -> env.getMat(args[0]).copyTo(env.mat) }
    }

    fun newPutCommand(args: Array<String>): Command {
        return Command { env -> env.putMat(args[0], env.mat) }
    }

}
