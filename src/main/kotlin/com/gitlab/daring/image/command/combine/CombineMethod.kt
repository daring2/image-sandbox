package com.gitlab.daring.image.command.combine

import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_core.Mat

internal enum class CombineMethod(val func: CombineFunction) {

    Max(opencv_core::max),
    Min(opencv_core::min),
    Add(opencv_core::add),
    Subtract(opencv_core::subtract),
    AbsDiff(opencv_core::absdiff),
    And(opencv_core::bitwise_and),
    Or(opencv_core::bitwise_or),
    Xor(opencv_core::bitwise_xor);

}

typealias CombineFunction = (Mat, Mat, Mat) -> Unit