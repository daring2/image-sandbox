package com.gitlab.daring.image.command

import com.gitlab.daring.image.MainContext.commandRegistry

object CommandScriptUtils {

    @JvmStatic
    fun parseScript(script: String): ScriptCommand {
        return commandRegistry.parseScript(script)
    }

    fun runScript(env: CommandEnv, script: String) {
        parseScript(script).execute(env)
    }

    fun commandString(cmd: String, vararg args: Any): String {
        return "$cmd(${args.joinToString("," )});"
    }

    fun runCommand(env: CommandEnv, cmd: String, vararg args: Any) {
        runScript(env, commandString(cmd, *args))
    }

}