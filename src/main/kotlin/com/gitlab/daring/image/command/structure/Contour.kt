package com.gitlab.daring.image.command.structure

import org.bytedeco.javacpp.opencv_core.Mat
import java.util.*

class Contour(val mat: Mat) {

    val metrics = EnumMap<ContourMetric, Double>(ContourMetric::class.java)

    fun getMetric(m: ContourMetric): Double {
        return metrics.computeIfAbsent(m) { m.calculate(mat) }
    }

}
