package com.gitlab.daring.sandbox.image.video;

import com.gitlab.daring.sandbox.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacpp.opencv_videoio.VideoWriter;
import static com.gitlab.daring.sandbox.image.util.VideoUtils.*;
import static java.lang.Integer.parseInt;

@SuppressWarnings("WeakerAccess")
public class BaseVideoProcessor extends BaseComponent implements AutoCloseable {

	public final VideoCapture capture = createCapture();
	public final long delay = getFrameDelay(capture, config.getInt("defaultDelay"));
	public final Size size = getFrameSize(capture);
	public final VideoWriter writer = createWriter();

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

	@Override
	public void close() throws Exception {
		capture.release();
		writer.release();
	}

}
