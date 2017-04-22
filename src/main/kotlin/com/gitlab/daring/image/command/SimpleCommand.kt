package com.gitlab.daring.image.command

import com.gitlab.daring.image.command.parameter.IntParam
import org.bytedeco.javacpp.opencv_core.Mat
import java.util.function.Consumer

class SimpleCommand(args: Array<String>) : KBaseCommand(args) {

    var func: (CommandEnv) -> Unit = {}

    fun withFunc(f: (Mat) -> Unit): SimpleCommand {
        func = { env -> f(env.mat) }
        return this
    }

    fun withFunc(c: Consumer<Mat>) = withFunc(c::accept)

    fun withFunc(n: IntParam, f: (Mat) -> Unit): SimpleCommand {
        func = { env -> for (i in 0..n.value) f(env.mat) }
        return this
    }

    fun withFunc(f: (Mat, Mat) -> Unit): SimpleCommand {
        val d = Mat()
        func = { env -> f(env.mat, d); env.mat = d }
        return this
    }

    fun withSetFunc(f: (Mat) -> Mat): SimpleCommand {
        func = { env -> env.mat = f(env.mat) }
        return this
    }

    override fun execute(env: CommandEnv) = func(env)

}