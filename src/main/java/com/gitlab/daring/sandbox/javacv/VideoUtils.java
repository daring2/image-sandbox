package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;

import static org.bytedeco.javacpp.opencv_videoio.CAP_PROP_FRAME_HEIGHT;
import static org.bytedeco.javacpp.opencv_videoio.CAP_PROP_FRAME_WIDTH;

class VideoUtils {

	static final int XVID = 1145656920;

	static Size getVideoSize(VideoCapture c) {
		return new Size((int) c.get(CAP_PROP_FRAME_WIDTH), (int) c.get(CAP_PROP_FRAME_HEIGHT));
	}

	private VideoUtils() {
	}

}
