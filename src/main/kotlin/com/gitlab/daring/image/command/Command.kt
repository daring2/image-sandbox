package com.gitlab.daring.image.command

import com.gitlab.daring.image.command.parameter.CommandParam

import java.util.Collections.emptyList

interface Command : AutoCloseable {

    fun execute(env: CommandEnv)

    val params: List<CommandParam<*>> get() = emptyList()

    val isCacheable get() = true

    fun isEnabled(env: CommandEnv) = env.task == env.curTask

    override fun close() {}

}
