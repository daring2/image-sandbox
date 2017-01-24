package com.gitlab.daring.sandbox.image.video;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacpp.opencv_videoio.VideoWriter;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;
import static com.gitlab.daring.sandbox.image.util.ConfigUtils.getIntOpt;
import static com.gitlab.daring.sandbox.image.util.VideoUtils.*;
import static java.lang.Integer.parseInt;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

@SuppressWarnings("WeakerAccess")
public abstract class BaseVideoProcessor extends BaseComponent implements AutoCloseable {

	protected final VideoCapture capture = createCapture();
	protected final long delay = getFrameDelay(capture, config.getInt("defaultDelay"));
	protected final Size size = getFrameSize(capture);
	protected final VideoWriter writer = createWriter();
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

	protected VideoWriter createWriter() {
		String file = config.getString("output");
		if (file.isEmpty()) return null;
		int codec = getCodec(config.getString("outputCodec"));
		VideoWriter w = new VideoWriter();
		if (!w.open(file, codec, 1000.0 / delay, size, true))
			throw new RuntimeException("Cannot create VideoWriter");
		return w;
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
		while (capture.read(inputMat) && isStarted()) {
			processFrame();
			Thread.sleep(delay);
		}
	}

	protected boolean isStarted() {
		return frame != null && frame.isVisible();
	}

	protected abstract void processFrame();

	@Override
	public void close() throws Exception {
		capture.release();
		if (writer != null) writer.release();
		if (frame != null) frame.dispose();
	}

}
