package com.gitlab.daring.image.video;

import com.gitlab.daring.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacpp.opencv_videoio.VideoWriter;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import java.io.File;

import static com.gitlab.daring.image.config.ConfigUtils.getIntOpt;
import static com.gitlab.daring.image.swing.SwingUtils.runInEdt;
import static com.gitlab.daring.image.video.VideoUtils.*;
import static java.lang.Integer.parseInt;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static org.apache.commons.io.FileUtils.deleteQuietly;

public abstract class BaseVideoProcessor extends BaseComponent implements AutoCloseable {

	protected final VideoCapture capture = createCapture();
	protected final int fps = getVideoFps(capture, config.getInt("fps"));
	protected final Size size = getFrameSize(capture);
	protected final VideoWriter writer = new VideoWriter();
	protected final CanvasFrame frame =  createFrame();

	protected final ToMat matConverter = new ToMat();
	protected final Mat inputMat = new Mat();

	public BaseVideoProcessor(String configPath) {
		super(configPath);
	}

	protected VideoCapture createCapture() {
		String in = config.getString("input");
		return in.contains(".") ? new VideoCapture(in) : new VideoCapture(parseInt(in));
	}

	protected CanvasFrame createFrame() {
		String title = config.getString("title");
		int gamma = getIntOpt(config, "gamma", 1);
		CanvasFrame f = new CanvasFrame(title, gamma);
		f.setCanvasSize(size.width(), size.height());
		f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		return f;
	}

	public void start() throws Exception {
		openWriter();
		while (capture.read(inputMat) && isStarted()) {
			processFrame();
			Thread.sleep(1000 / fps);
		}
	}

	protected void openWriter() {
		String file = config.getString("output");
		if (file.isEmpty()) return;
		deleteQuietly(new File(file));
		int codec = getCodec(config.getString("outputCodec"));
		if (!writer.open(file, codec, fps, size, true))
			throw new RuntimeException("Cannot create VideoWriter");
	}

	protected boolean isStarted() {
		return frame != null && frame.isVisible();
	}

	protected abstract void processFrame();

	public CanvasFrame getFrame() {
		return frame;
	}

	protected void showImage(Mat m) {
		runInEdt(() -> frame.showImage(matConverter.convert(m)));
	}

	@Override
	public void close() throws Exception {
		capture.release();
		writer.release();
		if (frame != null) frame.dispose();
	}

}
