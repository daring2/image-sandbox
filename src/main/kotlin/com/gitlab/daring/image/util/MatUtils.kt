package com.gitlab.daring.image.util

import com.gitlab.daring.image.component.BaseCanvasFrame
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_imgcodecs.imread
import javax.swing.WindowConstants.DISPOSE_ON_CLOSE

object MatUtils {

    @JvmStatic
    infix fun Mat.show(title: String) {
        val frame = BaseCanvasFrame(title)
        frame.defaultCloseOperation = DISPOSE_ON_CLOSE
        frame.showMat(this)
    }

    fun loadAndShow(file: String): Mat {
        return imread(file).apply { this show file }
    }

}