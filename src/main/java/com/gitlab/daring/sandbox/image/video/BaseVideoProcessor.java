package com.gitlab.daring.sandbox.image.video;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacpp.opencv_videoio.VideoWriter;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;
import static com.gitlab.daring.sandbox.image.util.VideoUtils.*;
import static java.lang.Integer.parseInt;

@SuppressWarnings("WeakerAccess")
public abstract class BaseVideoProcessor extends BaseComponent implements AutoCloseable {

	protected final VideoCapture capture = createCapture();
	protected final long delay = getFrameDelay(capture, config.getInt("defaultDelay"));
	protected final Size size = getFrameSize(capture);
	protected final VideoWriter writer = createWriter();

	protected final ToMat matConverter = new ToMat();
	protected final Mat inputMat = new Mat();

	public BaseVideoProcessor(String configPath) {
		super(configPath);
	}

	protected VideoCapture createCapture() {
		String in = config.getString("input");
		return in.contains(".") ? new VideoCapture(in) : new VideoCapture(parseInt(in));
	}

	protected VideoWriter createWriter() {
		String file = config.getString("output");
		if (file.isEmpty()) return null;
		int codec = getCodec(config.getString("outputCodec"));
		VideoWriter w = new VideoWriter();
		if (!w.open(file, codec, 1000.0 / delay, size, true))
			throw new RuntimeException("Cannot create VideoWriter");
		return w;
	}

	public void start() throws Exception {
		while (capture.read(inputMat) && isStarted()) {
			processFrame();
			Thread.sleep(delay);
		}
	}

	protected abstract void processFrame();

	protected boolean isStarted() {
		return true;
	}

	@Override
	public void close() throws Exception {
		capture.release();
		writer.release();
	}

}
