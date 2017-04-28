package com.gitlab.daring.image.util

import com.gitlab.daring.image.util.GeometryUtils.getCenterRect
import com.gitlab.daring.image.util.OpencvConverters.toJava
import com.gitlab.daring.image.util.OpencvConverters.toOpencv
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_core.Scalar
import java.awt.Color
import java.awt.Rectangle

object ImageUtils {

    fun centerRect(m: Mat, rectSize: Double): Rectangle {
        return getCenterRect(toJava(m.size()), rectSize)
    }

    fun cropMat(m: Mat, r: Rectangle): Mat {
        return m.apply(toOpencv(r))
    }

    fun cropCenter(m: Mat, rectSize: Double): Mat {
        if (rectSize == 1.0) return m
        return cropMat(m, centerRect(m, rectSize))
    }

    fun smat(v: Int): Mat {
        return Mat(byteArrayOf(v.toByte()), false)
    }

    fun newScalar(color: String): Scalar {
        val c = Color.decode(color)
        return Scalar(c.blue.toDouble(), c.green.toDouble(), c.red.toDouble(), 0.0)
    }

}
