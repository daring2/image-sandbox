package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_videoio.VideoCapture;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import static java.lang.Thread.sleep;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static org.bytedeco.javacpp.opencv_videoio.CAP_PROP_FRAME_HEIGHT;
import static org.bytedeco.javacpp.opencv_videoio.CAP_PROP_FRAME_WIDTH;

public class VideoSandbox {

	public static void main(String[] args) throws Exception {
//		VideoCapture cap = new VideoCapture("data/bike.avi");
		VideoCapture cap = new VideoCapture(0);
		CanvasFrame frame = new CanvasFrame("Videa", 1);
		frame.setCanvasSize((int) cap.get(CAP_PROP_FRAME_WIDTH), (int) cap.get(CAP_PROP_FRAME_HEIGHT));
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		OpenCVFrameConverter.ToMat conv = new OpenCVFrameConverter.ToMat();
		long delay = 100; // Math.round(1000 / cap.get(CAP_PROP_FPS));
		Mat m = new Mat();
		while (cap.read(m)) {
			frame.showImage(conv.convert(m));
			sleep(delay);
		}
		cap.release();
	}

}
