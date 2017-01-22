package com.gitlab.daring.sandbox.image;

import com.typesafe.config.Config;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import static com.gitlab.daring.sandbox.image.util.ConfigUtils.defaultConfig;
import static com.gitlab.daring.sandbox.image.util.ImageUtils.buildMat;
import static com.gitlab.daring.sandbox.image.util.SwingUtils.newButton;
import static com.gitlab.daring.sandbox.image.util.VideoUtils.getFrameDelay;
import static com.gitlab.daring.sandbox.image.util.VideoUtils.newFrame;
import static java.awt.BorderLayout.SOUTH;
import static java.lang.Integer.parseInt;
import static org.bytedeco.javacpp.opencv_imgproc.*;

@SuppressWarnings("WeakerAccess")
class ShotAssistant implements AutoCloseable {

	final Config config = defaultConfig().getConfig("gmv.ShotAssistant");

	final VideoCapture capture = createVideoCapture();
	final long delay = getFrameDelay(capture, 50);
	final CanvasFrame frame = createFrame();

	final ToMat converter = new ToMat();
	final Mat mat = new Mat();
	Mat sample;

	private VideoCapture createVideoCapture() {
		String in = config.getString("input");
		return in.contains(".") ? new VideoCapture(in) : new VideoCapture(parseInt(in));
	}

	private CanvasFrame createFrame() {
		CanvasFrame f = newFrame(capture, "Video");
		f.add(newButton("Снимок", this::saveSample), SOUTH);
		f.validate();
		return f;
	}

	void saveSample() {
		Mat m = buildMat(r -> cvtColor(mat, r, COLOR_BGR2GRAY));
		Canny(m, m, 30, 60);
		sample = buildMat(r -> cvtColor(m, r, COLOR_GRAY2BGR));
	}

	void start() throws Exception {
		while (capture.read(mat) && frame.isVisible()) {
			Mat m = buildFrame();
			frame.showImage(converter.convert(m));
			Thread.sleep(delay);
		}
	}

	private Mat buildFrame() {
		return sample != null ? buildMat(r -> opencv_core.add(mat, sample, r)) : mat;
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
