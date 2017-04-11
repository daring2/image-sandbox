package com.gitlab.daring.image.command.template

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.opencv.size
import com.gitlab.daring.image.util.OpencvConverters.newRect
import org.bytedeco.javacpp.DoublePointer
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_imgproc.matchTemplate

internal class MatchTemplateCommand(args: Array<String>) : KBaseCommand(args) {

    val tkey = stringParam("tm")
    val matchMethod = enumParam(MatchMethod.CCORR_NORMED)

    val rm = Mat()
    val valueRef = DoublePointer(1)
    val pointRef = Point()
    val mask = Mat()

    override fun execute(env: CommandEnv) {
        val tm = env.getMat(tkey.v)
        val method = matchMethod.v
        matchTemplate(env.mat, tm, rm, method.ordinal)
        if (method.isMinBest) {
            minMaxLoc(rm, valueRef, null, pointRef, null, mask)
        } else {
            minMaxLoc(rm, null, valueRef, null, pointRef, mask)
        }
        env.vars.put("matchRect", newRect(pointRef, tm.size))
        env.vars.put("matchValue", valueRef.get())
    }

}