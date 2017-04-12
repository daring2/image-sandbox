package com.gitlab.daring.image.opencv

import com.gitlab.daring.image.util.ImageUtils.smat
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_imgproc.COLOR_GRAY2BGR
import org.bytedeco.javacpp.opencv_imgproc.cvtColor

val Mat.size get() = size()
val Mat.width get() = cols()
val Mat.height get() = rows()
val Mat.type get() = type()
val Mat.channels get() = channels()
val Mat.empty get() = empty()
val Mat.gray get() = channels == 1

fun Mat.abs() = opencv_core.abs(this).asMat()
fun Mat.setTo(v: Int) = setTo(smat(v))

fun Mat.toColored(): Mat {
    val m = this
    if (!gray) return m
    return Mat().apply { cvtColor(m, this, COLOR_GRAY2BGR) }
}
