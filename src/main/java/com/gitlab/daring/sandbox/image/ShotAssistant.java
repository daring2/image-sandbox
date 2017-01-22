package com.gitlab.daring.sandbox.image;

import com.typesafe.config.Config;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import static com.gitlab.daring.sandbox.image.util.ConfigUtils.defaultConfig;
import static com.gitlab.daring.sandbox.image.util.VideoUtils.getFrameDelay;
import static com.gitlab.daring.sandbox.image.util.VideoUtils.newFrame;
import static java.lang.Integer.parseInt;

@SuppressWarnings("WeakerAccess")
class ShotAssistant implements AutoCloseable {

	final Config config = defaultConfig().getConfig("gmv.ShotAssistant");

	final VideoCapture capture = createVideoCapture();
	final long delay = getFrameDelay(capture, 50);
	final CanvasFrame frame = newFrame(capture, "Video");

	final Mat mat = new Mat();
	final ToMat converter = new ToMat();

	private VideoCapture createVideoCapture() {
		String f = config.getString("videoFile");
		return f.contains(".") ? new VideoCapture(f) : new VideoCapture(parseInt(f));
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
		try (ShotAssistant app = new ShotAssistant()) {
			app.start();
		}
	}

}
