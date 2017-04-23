package com.gitlab.daring.image.command

import org.bytedeco.javacpp.opencv_core.Mat

object CommandScriptUtils {

    fun parseScript(script: String): ScriptCommand {
        return CommandRegistry.Instance.parseScript(script)
    }

    fun runScript(env: CommandEnv, script: String) {
        parseScript(script).execute(env)
    }

    fun commandString(cmd: String, vararg args: Any): String {
        return "$cmd(${args.joinToString("," )});"
    }

    fun runCommand(env: CommandEnv, cmd: String, vararg args: Any): Mat {
        runScript(env, commandString(cmd, *args))
        return env.mat
    }

}