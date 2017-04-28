package com.gitlab.daring.image.sandbox

import com.gitlab.daring.image.concurrent.TaskExecutor
import com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim
import java.util.Collections.emptyList

internal class ScriptExecutor(val sb: ImageSandbox) : AutoCloseable {

    val script = sb.mp.script
    val env = script.env
    val taskExec = TaskExecutor()
    var files = emptyList<String>()

    fun executeAsync() {
        taskExec.executeAsync { this.execute() }
    }

    fun execute() {
        files = splitAndTrim(sb.mp.filesParam.value, ",").toList()
        if (files.isNotEmpty()) {
            for (i in 0..files.size) runScript(i)
            script.runTask("combine")
        } else {
            script.execute()
        }
    }

    fun runScript(i: Int) {
        val file = files[i]
        env.putVar("i", i).putVar("file", file)
        env.task = ""
        script.runCommand("read", file)
        script.execute()
        env.putMat("\$i", env.mat)
    }

    override fun close() {
        taskExec.close()
    }

}