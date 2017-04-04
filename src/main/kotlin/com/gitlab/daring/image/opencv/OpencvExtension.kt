package com.gitlab.daring.image.opencv

import org.bytedeco.javacpp.opencv_core.Mat

val Mat.size get() = this.size()
val Mat.width get() = this.cols()
val Mat.empty get() = this.empty()
