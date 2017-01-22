package com.gitlab.daring.sandbox.image;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import static com.gitlab.daring.sandbox.image.util.VideoUtils.getFrameDelay;
import static com.gitlab.daring.sandbox.image.util.VideoUtils.newFrame;
import static org.apache.commons.lang3.StringUtils.isBlank;

@SuppressWarnings("WeakerAccess")
class ShotAssistant implements AutoCloseable {

	final VideoCapture capture;
	final Mat mat = new Mat();
	final ToMat converter = new ToMat();
	final long delay;
	final CanvasFrame frame;

	private ShotAssistant(String file) {
		capture = isBlank(file) ? new VideoCapture(0) : new VideoCapture(file);
		delay = getFrameDelay(capture, 50);
		frame = newFrame(capture, "Video");
	}

	void start() throws Exception {
		while (capture.read(mat) && frame.isVisible()) {
			frame.showImage(converter.convert(mat));
			Thread.sleep(delay);
		}
	}

	public void close() throws Exception {
		capture.release();
		frame.dispose();
	}

	public static void main(String[] args) throws Exception {
		String file = args.length > 0 ? args[0] : "";
		try (ShotAssistant app = new ShotAssistant(file)) {
			app.start();
		}
	}

}
