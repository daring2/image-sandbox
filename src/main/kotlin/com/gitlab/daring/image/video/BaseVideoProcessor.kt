package com.gitlab.daring.image.video

import com.gitlab.daring.image.config.ConfigUtils.defaultConfig
import com.gitlab.daring.image.swing.SwingUtils.runInEdt
import com.gitlab.daring.image.video.VideoUtils.getCodec
import com.gitlab.daring.image.video.VideoUtils.getFrameSize
import com.gitlab.daring.image.video.VideoUtils.getVideoFps
import org.apache.commons.io.FileUtils.deleteQuietly
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_videoio.VideoCapture
import org.bytedeco.javacpp.opencv_videoio.VideoWriter
import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.OpenCVFrameConverter
import java.io.File
import javax.swing.WindowConstants.DISPOSE_ON_CLOSE

abstract class BaseVideoProcessor(val configPath: String): AutoCloseable {

    val config = defaultConfig.getConfig(configPath)
    val capture = createCapture()
    val fps = getVideoFps(capture, config.getInt("fps"))
    val size = getFrameSize(capture)
    val writer = VideoWriter()
    val frame = createFrame()

    val matConverter = OpenCVFrameConverter.ToMat()
    val inputMat = Mat()

    protected fun createCapture(): VideoCapture {
        val i = config.getString("input")
        return if (i.contains(".")) VideoCapture(i) else VideoCapture(i.toInt())
    }

    protected fun createFrame(): CanvasFrame {
        val title = config.getString("title")
        val gamma = config.getInt("gamma")
        val f = CanvasFrame(title, gamma.toDouble())
        f.setCanvasSize(size.width(), size.height())
        f.setDefaultCloseOperation(DISPOSE_ON_CLOSE)
        return f
    }

    open fun start() {
        openWriter()
        while (capture.read(inputMat) && isStarted()) {
            processFrame()
            Thread.sleep((1000 / fps).toLong())
        }
    }

    protected fun openWriter() {
        val file = config.getString("output")
        if (file.isEmpty()) return
        deleteQuietly(File(file))
        val codec = getCodec(config.getString("outputCodec"))
        if (!writer.open(file, codec, fps.toDouble(), size, true))
            throw RuntimeException("Cannot create VideoWriter")
    }

    fun isStarted(): Boolean {
        return frame.isVisible
    }

    protected abstract fun processFrame()


    protected fun showImage(m: Mat) {
        runInEdt { frame.showImage(matConverter.convert(m)) }
    }

    override fun close() {
        capture.release()
        writer.release()
        frame.dispose()
    }

}