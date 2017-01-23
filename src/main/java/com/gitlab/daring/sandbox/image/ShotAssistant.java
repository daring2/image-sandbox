package com.gitlab.daring.sandbox.image;

import com.typesafe.config.Config;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacpp.opencv_videoio.VideoWriter;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import javax.swing.*;
import java.awt.BorderLayout;

import static com.gitlab.daring.sandbox.image.util.ConfigUtils.defaultConfig;
import static com.gitlab.daring.sandbox.image.util.ImageUtils.buildMat;
import static com.gitlab.daring.sandbox.image.util.SwingUtils.newButton;
import static com.gitlab.daring.sandbox.image.util.VideoUtils.*;
import static java.awt.BorderLayout.*;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

@SuppressWarnings("WeakerAccess")
class ShotAssistant implements AutoCloseable {

	final Config config = defaultConfig().getConfig("gmv.ShotAssistant");

	final VideoCapture capture = createVideoCapture();
	final long delay = getFrameDelay(capture, 50);
	final VideoWriter writer = newWriter(capture, config.getString("output"), delay);
	final JLabel statusField = new JLabel();
	final CanvasFrame frame = createFrame();

	final ToMat converter = new ToMat();
	final Mat inputMat = new Mat();
	final Mat sampleMat = new Mat();// new Mat(size, CV_8UC3);
	final Rect roi = new Rect(213, 160, 213, 160);
	final Mat displayMat = new Mat();

	private VideoCapture createVideoCapture() {
		String in = config.getString("input");
		return in.contains(".") ? new VideoCapture(in) : new VideoCapture(parseInt(in));
	}

	private CanvasFrame createFrame() {
		CanvasFrame f = newFrame(capture, "Video");
		JPanel p = new JPanel(new BorderLayout());
		p.add(statusField, CENTER);
		p.add(newButton("Снимок", this::saveSample), EAST);
		f.add(p, SOUTH);
		f.validate();
		return f;
	}

	void start() throws Exception {
		while (capture.read(inputMat) && frame.isVisible()) {
			boolean sm = !sampleMat.empty();
			bitwise_or(inputMat, sm ? sampleMat : inputMat, displayMat);
			boolean cr = sm && checkSample();
			rectangle(displayMat, roi, cr ? Scalar.GREEN : Scalar.BLUE);
			if (writer.isOpened()) writer.write(displayMat);
			frame.showImage(converter.convert(displayMat));
			Thread.sleep(delay);
		}
	}

	private Mat buildSample() {
		Mat m = buildMat(r -> cvtColor(inputMat, r, COLOR_BGR2GRAY));
//		Canny(m, m, 30, 60);
		morphologyEx(m, m, MORPH_GRADIENT, new Mat());
		threshold(m, m, 40, 255, THRESH_BINARY);
		return m;
	}

	void saveSample() {
		cvtColor(buildSample(), sampleMat, COLOR_GRAY2BGR);
	}

	private boolean checkSample() {
		Mat sm = buildMat(r -> cvtColor(sampleMat, r, COLOR_BGR2GRAY));
		Mat cm = buildSample();
		Mat mr = buildMat(r -> matchTemplate(cm, new Mat(sm, roi), r, CV_TM_CCORR));
		DoublePointer mv = new DoublePointer(1);
		Point mp = new Point();
		minMaxLoc(mr, null, mv, null, mp, new Mat());
		statusField.setText("result: " + mv.get());
		return mv.get() > 7 && abs(roi.x() - mp.x()) < 10 && abs(roi.y() - mp.y()) < 10;
	}

	public void close() throws Exception {
		capture.release();
		writer.release();
		frame.dispose();
	}

	public static void main(String[] args) throws Exception {
		try (ShotAssistant app = new ShotAssistant()) {
			app.start();
		}
	}

}
