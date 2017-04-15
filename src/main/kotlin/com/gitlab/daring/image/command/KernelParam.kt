package com.gitlab.daring.image.command

import com.gitlab.daring.image.opencv.size
import com.gitlab.daring.image.opencv.width
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_core.Size
import org.bytedeco.javacpp.opencv_imgproc.CV_SHAPE_RECT
import org.bytedeco.javacpp.opencv_imgproc.getStructuringElement

class KernelParam(cmd: BaseCommand) {

    val p = cmd.intParam(1, "0-10")

    @Volatile var v = Mat()

    val size: Size get() = v.size
    val width get() = v.width

    init {
        p.changeEvent.onFire(Runnable(this::update))
        update()
    }

    fun update() {
        val w = Math.max(p.v * 2 + 1, 3)
        v = getStructuringElement(CV_SHAPE_RECT, Size(w, w))
    }

}