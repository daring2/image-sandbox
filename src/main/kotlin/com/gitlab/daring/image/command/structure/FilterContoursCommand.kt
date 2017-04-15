package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import java.lang.Double.NaN

internal class FilterContoursCommand(params: Array<String>) : KBaseCommand(params) {

    val metric = enumParam(ContourMetric.Length)
    val minValue = doubleParam(NaN, "0-1000")
    val maxValue = doubleParam(NaN, "0-1000")
    val offset = intParam(0, "0-100")
    val limit = intParam(Int.MAX_VALUE, "0-100")

    override fun execute(env: CommandEnv) {
        if (minValue.v.isNaN() && maxValue.v.isNaN()) return
        val m = metric.v
        val mc = m.comparator.reversed()
        env.contours = env.contours.filter { c ->
            val mv = c.getMetric(m)
            mv >= minValue.v && (mv < maxValue.v || maxValue.v.isNaN())
        }.sortedWith(mc).drop(offset.v).take(limit.v)
    }

}
