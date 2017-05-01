package com.gitlab.daring.image.command

import com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim
import org.bytedeco.javacpp.opencv_core.Mat

object CommandUtils {

    fun newEnvCommand(f: (CommandEnv) -> Unit): Command {
        return SimpleCommand(emptyArray()).apply { func = f }
    }

    fun newCommand(c: (Mat) -> Unit): Command {
        return SimpleCommand(emptyArray()).withFunc(c)
    }

    fun splitScript(script: String): List<String> {
        return splitAndTrim(script, "\n")
                .filterNot { it.startsWith("//") }
                .flatMap { splitAndTrim(it, ";") }
                .filterNot { it.startsWith("-") }
    }

    fun parseArgs(argStr: String): Array<String> {
        return splitAndTrim(argStr, ",").toTypedArray()
    }

    fun parseIntParams(ps: Array<String>) = ps.map(String::toInt)

}