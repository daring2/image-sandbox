package com.gitlab.daring.image.command.env

import com.gitlab.daring.image.command.Command
import com.gitlab.daring.image.command.CommandRegistry
import com.gitlab.daring.image.command.CommandUtils.newEnvCommand
import com.gitlab.daring.image.command.SimpleCommand

import org.bytedeco.javacpp.opencv_imgcodecs.imwrite

object EnvCommands {

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
        c.func = { env -> imwrite(env.eval(file), env.getMat(key)) }
        return c
    }

    fun newGetCommand(args: Array<String>): Command {
        return newEnvCommand { env -> env.getMat(args[0]).copyTo(env.mat) }
    }

    fun newPutCommand(args: Array<String>): Command {
        return newEnvCommand { env -> env.putMat(args[0], env.mat) }
    }

}
