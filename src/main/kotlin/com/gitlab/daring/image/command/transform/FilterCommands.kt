package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.Command
import com.gitlab.daring.image.command.CommandRegistry
import com.gitlab.daring.image.command.KernelParam
import com.gitlab.daring.image.command.SimpleCommand
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.*

internal object FilterCommands {
    
    fun register(r: CommandRegistry) {
        r.register("blur", this::blurCommand)
        r.register("gaussianBlur", this::gaussianBlurCommand)
        r.register("medianBlur", this::medianBlurCommand)
        r.register("bilateralFilter", this::bilateralFilterCommand)
        r.register("sharpen", this::sharpenCommand)
        r.register("laplacian", ::LaplacianCommand)
        r.register("removeLight", ::RemoveLightCommand)
    }

    fun blurCommand(args: Array<String>): Command {
        val c = SimpleCommand(args)
        val kp = KernelParam(c)
        return c.withFunc { m, d -> blur(m, d, kp.size) }
    }

    fun gaussianBlurCommand(args: Array<String>): Command {
        val c = SimpleCommand(args)
        val kp = KernelParam(c)
        val sigma = c.doubleParam(0.0, "0-10")
        val n = c.intParam(1, "0-10")
        return c.withFunc(n) { m -> GaussianBlur(m, m, kp.size, sigma.v) }
    }

    fun medianBlurCommand(args: Array<String>): Command {
        val c = SimpleCommand(args)
        val kp = KernelParam(c)
        val n = c.intParam(1, "0-10")
        return c.withFunc(n) { m -> medianBlur(m, m, kp.width) }
    }

    fun bilateralFilterCommand(args: Array<String>): Command {
        val c = SimpleCommand(args)
        val dp = c.intParam(5, "0-50")
        val sp1 = c.doubleParam(10.0, "0-200")
        val sp2 = c.doubleParam(10.0, "0-200")
        return c.withFunc { m, d -> bilateralFilter(m, d, dp.v, sp1.v, sp2.v) }
    }

    fun sharpenCommand(args: Array<String>): Command {
        val c = SimpleCommand(args)
        val n = c.intParam(1, "0-10")
        val rm = Mat()
        return c.withFunc(n) { m ->
            GaussianBlur(m, rm, Size(), 3.0)
            addWeighted(m, 1.5, rm, -0.5, 0.0, m)
        }
    }

}
