package com.gitlab.daring.image.command

import com.gitlab.daring.image.command.CommandScriptUtils.commandString
import com.gitlab.daring.image.command.CommandScriptUtils.parseScript
import com.gitlab.daring.image.command.CommandScriptUtils.runScript
import com.gitlab.daring.image.event.ValueEvent
import org.bytedeco.javacpp.opencv_core.Mat

class CommandScript {

    val env = CommandEnv()
    val errorEvent = ValueEvent<Exception>()

    var command = parseScript("")

    var text
        get() = command.script
        set(v) = tryRun { command = parseScript(v) }

    init {
        env.startEvent.addListener { startScript() }
    }

    private fun startScript() {
        command.execute(env)
        env.finishEvent.fire(env)
    }

    fun runTask(task: String) = tryRun {
        env.task = task
        env.startEvent.fire(env)
    }

    fun execute() = runTask("")

    fun runTask(task: String, mat: Mat?): Mat {
        env.mat = mat?.clone() ?: Mat()
        runTask(task)
        return env.mat.clone()
    }

    fun runCommand(cmd: String, vararg args: Any): Mat {
        tryRun { runScript(env, commandString(cmd, *args)) }
        return env.mat
    }

    fun tryRun(f: () -> Unit) {
        try { f.invoke() } catch (e: Exception) { errorEvent.fire(e) }
    }

}