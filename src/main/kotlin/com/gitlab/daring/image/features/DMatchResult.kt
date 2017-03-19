package com.gitlab.daring.image.features

import org.bytedeco.javacpp.opencv_core.DMatchVector

data class DMatchResult(
        val points1: KeyPointList,
        val points2: KeyPointList,
        val matches: DMatchVector = DMatchVector()
)