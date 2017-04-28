package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.Command
import com.gitlab.daring.image.command.CommandRegistry

import com.gitlab.daring.image.command.CommandUtils.newCommand
import org.bytedeco.javacpp.opencv_core.bitwise_not
import org.bytedeco.javacpp.opencv_imgproc.equalizeHist

object TransformCommands {

    @JvmStatic
    fun register(r: CommandRegistry) {
        r.register("equalizeHist", this::equalizeHistCommand)
        r.register("bitwiseNot", this::bitwiseNotCommand)
        r.register("keepRange", ::KeepRangeCommand)
        r.register("convert", ::ConvertCommand)
        r.register("morphology", ::MorphologyCommand)
        r.register("normalize", ::NormalizeCommand)
        r.register("normalizeMean", ::NormalizeMeanCommand)
        r.register("applyClahe", ::CLAHECommand)
        ThresholdCommands.register(r)
        FilterCommands.register(r)
        GeometricCommands.register(r)
    }

    fun equalizeHistCommand(ps: Array<String>): Command {
        return newCommand { m -> equalizeHist(m, m) }
    }

    fun bitwiseNotCommand(ps: Array<String>): Command {
        return newCommand { m -> bitwise_not(m, m) }
    }

}
