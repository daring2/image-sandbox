package com.gitlab.daring.image.command

import com.gitlab.daring.image.command.parameter.CommandParam

class ScriptCommand(val script: String, val commands: List<Command>) : Command {

    override fun execute(env: CommandEnv) {
        commands.filter { it.isEnabled(env) }.forEach { it.execute(env) }
    }

    override fun getParams(): List<CommandParam<*>> {
        return commands.flatMap { it.params }
    }

    override fun close() {
        commands.forEach { it.close() }
    }

}