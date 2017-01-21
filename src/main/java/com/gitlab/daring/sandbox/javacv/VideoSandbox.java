package com.gitlab.daring.sandbox.javacv;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_videoio.*;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import static java.lang.Thread.sleep;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_videoio.*;

public class VideoSandbox {

	public static void main(String[] args) throws Exception {
//		VideoCapture cap = new VideoCapture("data/bike.avi");
		VideoCapture cap = new VideoCapture(0);
		OpenCVFrameConverter.ToMat conv = new OpenCVFrameConverter.ToMat();
		CanvasFrame frame1 = newFrame(cap, "Video");
		CanvasFrame frame2 = newFrame(cap, "Result");
		double fps = cap.get(CAP_PROP_FPS);
		long delay = fps > 0 ? Math.round(1000 / fps) : 100;
		Mat in = new Mat();
		Mat out = new Mat();
		while (cap.read(in)) {
			frame1.showImage(conv.convert(in));
			calcCanny(in, out);
			frame2.showImage(conv.convert(out));
			sleep(delay);
		}
		cap.release();
	}

	private static CanvasFrame newFrame(VideoCapture cap, String title) {
		CanvasFrame frame = new CanvasFrame("title", 1);
		frame.setCanvasSize((int) cap.get(CAP_PROP_FRAME_WIDTH), (int) cap.get(CAP_PROP_FRAME_HEIGHT));
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		return frame;
	}

	private static void calcCanny(Mat in, Mat out) {
		cvtColor(in, out, COLOR_BGR2GRAY);
		Canny(out, out, 50, 150/*, 3, true*/);
		threshold(out, out, 128, 255, THRESH_BINARY_INV);
	}

}
