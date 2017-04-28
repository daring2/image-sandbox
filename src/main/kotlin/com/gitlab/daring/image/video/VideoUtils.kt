package com.gitlab.daring.image.video

import org.bytedeco.javacpp.opencv_core.Size
import org.bytedeco.javacpp.opencv_videoio.*

import java.nio.ByteBuffer

import java.nio.ByteOrder.LITTLE_ENDIAN
import java.nio.charset.StandardCharsets.US_ASCII

object VideoUtils {

    val XVID = getCodec("XVID")

    fun getVideoFps(c: VideoCapture, defValue: Int): Int {
        val fps = c.get(CAP_PROP_FPS)
        return if (fps > 0) fps.toInt() else defValue
    }

    fun getFrameSize(c: VideoCapture): Size {
        val width = c.get(CAP_PROP_FRAME_WIDTH).toInt()
        val height = c.get(CAP_PROP_FRAME_HEIGHT).toInt()
        return Size(width, height)
    }

    fun getCodec(codec: String): Int {
        val bs = codec.toByteArray(US_ASCII)
        return ByteBuffer.wrap(bs).order(LITTLE_ENDIAN).int
    }

}
