package com.gitlab.daring.image.features

import org.bytedeco.javacpp.opencv_core.KeyPointVector
import org.bytedeco.javacpp.opencv_core.Mat

data class KeyPointList(
        val mat: Mat,
        val points: KeyPointVector = KeyPointVector(),
        val descriptors: Mat = Mat()
)