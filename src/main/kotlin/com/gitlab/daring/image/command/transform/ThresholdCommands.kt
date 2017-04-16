package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.Command
import com.gitlab.daring.image.command.CommandRegistry
import com.gitlab.daring.image.command.SimpleCommand
import com.gitlab.daring.image.util.ImageUtils.smat
import org.bytedeco.javacpp.opencv_core.inRange
import org.bytedeco.javacpp.opencv_imgproc.adaptiveThreshold
import org.bytedeco.javacpp.opencv_imgproc.threshold

internal object ThresholdCommands {

    @JvmStatic
    fun register(r: CommandRegistry) {
        r.register("threshold", this::thresholdCommand)
        r.register("adaptiveThreshold", this::adaptiveThresholdCommand)
        r.register("inRange", this::inRangeCommand)
    }

    fun thresholdCommand(args: Array<String>): Command {
        val c = SimpleCommand(args)
        val th = c.doubleParam(128.0, "0-255")
        val mv = c.doubleParam(255.0, "0-255")
        val tp = c.enumParam(ThresholdType.Bin)
        val fp = c.enumParam(ThresholdFlag.None)
        return c.withFunc { m -> threshold(m, m, th.v, mv.v, tp.vi + fp.vi * 8) }
    }

    fun adaptiveThresholdCommand(args: Array<String>): Command {
        val c = SimpleCommand(args)
        val mv = c.doubleParam(255.0, "0-255")
        val method = c.enumParam(AdaptiveMethod.Mean)
        val type = c.enumParam(ThresholdType.Bin)
        val bs = c.intParam(1, "0-50")
        val cf = c.intParam(0, "0-100")
        return c.withFunc { m -> adaptiveThreshold(m, m, mv.v, method.vi, type.vi, bs.v * 2 + 1, cf.dv) }
    }

    fun inRangeCommand(args: Array<String>): Command {
        val c = SimpleCommand(args)
        val lb = c.intParam(0, "0-255")
        val ub = c.intParam(255, "0-255")
        return c.withFunc { m -> inRange(m, smat(lb.v), smat(ub.v), m) }
    }

    enum class ThresholdType {
        Bin, BinInv, Trunc, ToZero, ToZeroInv
    }

    enum class ThresholdFlag {
        None, OTSU, Triangle
    }

    enum class AdaptiveMethod {
        Mean, Gaussian
    }

}
