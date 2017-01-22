package com.gitlab.daring.sandbox.image;

import com.typesafe.config.Config;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import static com.gitlab.daring.sandbox.image.util.ConfigUtils.defaultConfig;
import static com.gitlab.daring.sandbox.image.util.ImageUtils.buildMat;
import static com.gitlab.daring.sandbox.image.util.SwingUtils.newButton;
import static com.gitlab.daring.sandbox.image.util.VideoUtils.*;
import static java.awt.BorderLayout.SOUTH;
import static java.lang.Integer.parseInt;
import static org.bytedeco.javacpp.opencv_core.CV_8UC3;
import static org.bytedeco.javacpp.opencv_core.bitwise_or;
import static org.bytedeco.javacpp.opencv_imgproc.*;

@SuppressWarnings("WeakerAccess")
class ShotAssistant implements AutoCloseable {

	final Config config = defaultConfig().getConfig("gmv.ShotAssistant");

	final VideoCapture capture = createVideoCapture();
	final Size size = getFrameSize(capture);
	final long delay = getFrameDelay(capture, 50);
	final CanvasFrame frame = createFrame();

	final ToMat converter = new ToMat();
	final Mat inputMat = new Mat();
	final Mat sampleMat = new Mat(size, CV_8UC3);
	final Rect centerRect = new Rect(213, 160, 213, 160);
	final Mat displayMat = new Mat();

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
		Mat m = buildMat(r -> cvtColor(inputMat, r, COLOR_BGR2GRAY));
//		Canny(m, m, 30, 60);
		morphologyEx(m, m, MORPH_GRADIENT, new Mat());
		threshold(m, m, 20, 255, THRESH_BINARY);
		cvtColor(m, sampleMat, COLOR_GRAY2BGR);
	}

	void start() throws Exception {
		while (capture.read(inputMat) && frame.isVisible()) {
			bitwise_or(inputMat, sampleMat, displayMat);
			rectangle(displayMat, centerRect, Scalar.BLUE);
			frame.showImage(converter.convert(displayMat));
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
