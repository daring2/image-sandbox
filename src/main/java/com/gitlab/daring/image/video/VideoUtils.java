package com.gitlab.daring.image.video;

import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_videoio.*;

import java.nio.ByteBuffer;

import static java.nio.ByteOrder.LITTLE_ENDIAN;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.bytedeco.javacpp.opencv_videoio.*;

public class VideoUtils {

	public static final int XVID = getCodec("XVID");

	public static int getVideoFps(VideoCapture c, long defValue) {
		double fps = c.get(CAP_PROP_FPS);
		return (int) (fps > 0 ? fps : defValue);
	}

	public static Size getFrameSize(VideoCapture c) {
		return new Size((int) c.get(CAP_PROP_FRAME_WIDTH), (int) c.get(CAP_PROP_FRAME_HEIGHT));
	}

	public static int getCodec(String codec) {
		byte[] bs = codec.getBytes(US_ASCII);
		return ByteBuffer.wrap(bs).order(LITTLE_ENDIAN).getInt();
	}

	private VideoUtils() {
	}

}
