package com.gitlab.daring.image.swing

import com.gitlab.daring.image.swing.SwingUtils.runInEdt
import com.gitlab.daring.image.util.GeometryUtils.scaleToMax
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat
import java.awt.Dimension
import java.awt.Image

class BaseCanvasFrame(title: String) : CanvasFrame(title, 1.0) {

    val matConverter = ToMat()
    val maxSize = Dimension(1024, 768)

    init {
        isVisible = false
    }

    fun showMat(m: Mat) {
        val cm = matConverter.convert(m)
        runInEdt { showImage(cm) }
    }

    override fun showImage(image: Image?) {
        if (!isVisible) needInitialResize = true
        super.showImage(image)
        if (!isVisible) {
            resize()
            isVisible = true
        }
    }

    fun resize() {
        val d = scaleToMax(canvasSize, maxSize)
        setCanvasSize(d.width, d.height)
    }

}
