package com.gitlab.daring.sandbox.image.util;

import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_videoio.*;
import org.bytedeco.javacv.CanvasFrame;
import java.nio.ByteBuffer;
import static java.nio.ByteOrder.LITTLE_ENDIAN;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static org.bytedeco.javacpp.opencv_videoio.*;

@SuppressWarnings("WeakerAccess")
public class VideoUtils {

	public static final int XVID = 1145656920;

	public static long getFrameDelay(VideoCapture c, long defValue) {
		double fps = c.get(CAP_PROP_FPS);
		return fps > 0 ? Math.round(1000 / fps) : defValue;
	}

	public static Size getFrameSize(VideoCapture c) {
		return new Size((int) c.get(CAP_PROP_FRAME_WIDTH), (int) c.get(CAP_PROP_FRAME_HEIGHT));
	}

	public static CanvasFrame newFrame(VideoCapture c, String title) {
		CanvasFrame frame = new CanvasFrame(title, 1);
		Size size = getFrameSize(c);
		frame.setCanvasSize(size.width(), size.height());
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		return frame;
	}

	public static int getCodec(String codec) {
		byte[] bs = codec.getBytes(US_ASCII);
		return ByteBuffer.wrap(bs).order(LITTLE_ENDIAN).getInt();
	}

	private VideoUtils() {
	}

}
