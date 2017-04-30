package com.gitlab.daring.image.command

class ScriptCommand(
        val script: String,
        val commands: List<Command>
) : Command {

    override val params get() = commands.flatMap(Command::params)

    override fun execute(env: CommandEnv) {
        for (cmd in commands) {
            if (cmd.isEnabled(env)) cmd.execute(env)
        }
    }

    override fun close() {
        commands.forEach(AutoCloseable::close)
    }

}