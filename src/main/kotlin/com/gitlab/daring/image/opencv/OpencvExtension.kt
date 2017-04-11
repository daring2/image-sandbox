package com.gitlab.daring.image.opencv

import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_core.Mat

val Mat.size get() = size()
val Mat.width get() = cols()
val Mat.height get() = rows()
val Mat.channels get() = channels()
val Mat.empty get() = empty()
fun Mat.abs() = opencv_core.abs(this).asMat()
