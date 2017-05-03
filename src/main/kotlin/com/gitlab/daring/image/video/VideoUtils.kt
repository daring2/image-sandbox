package com.gitlab.daring.image.video

import org.bytedeco.javacpp.opencv_videoio.*
import java.awt.Dimension
import java.nio.ByteBuffer
import java.nio.ByteOrder.LITTLE_ENDIAN
import java.nio.charset.StandardCharsets.US_ASCII

object VideoUtils {

    val XVID = getCodec("XVID")

    @JvmStatic
    fun newVideoCapture(input: String): VideoCapture {
        return when {
            input.contains(".") -> VideoCapture(input)
            else -> VideoCapture(input.toInt())
        }
    }

    @JvmStatic
    fun getVideoFps(c: VideoCapture, defValue: Int): Int {
        val fps = c.get(CAP_PROP_FPS)
        return if (fps > 0) fps.toInt() else defValue
    }

    @JvmStatic
    fun getFrameSize(c: VideoCapture): Dimension {
        val width = c.get(CAP_PROP_FRAME_WIDTH).toInt()
        val height = c.get(CAP_PROP_FRAME_HEIGHT).toInt()
        return Dimension(width, height)
    }

    @JvmStatic
    fun getCodec(codec: String): Int {
        val bs = codec.toByteArray(US_ASCII)
        return ByteBuffer.wrap(bs).order(LITTLE_ENDIAN).int
    }

}
