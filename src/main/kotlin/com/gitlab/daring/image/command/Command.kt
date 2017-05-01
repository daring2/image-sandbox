package com.gitlab.daring.image.command

import com.gitlab.daring.image.command.parameter.CommandParam

interface Command : AutoCloseable {

    fun execute(env: CommandEnv)

    val params: List<CommandParam<*>>

    val cacheable get() = true

    fun isEnabled(env: CommandEnv) = env.task == env.curTask

    override fun close() {}

}
