package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.video.BaseVideoProcessor;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import javax.swing.*;
import java.awt.BorderLayout;
import static com.gitlab.daring.sandbox.image.util.SwingUtils.newButton;
import static java.awt.BorderLayout.*;
import static org.bytedeco.javacpp.opencv_core.bitwise_or;
import static org.bytedeco.javacpp.opencv_imgproc.*;

class ShotAssistant extends BaseVideoProcessor {

	final SampleBuilder sampleBuilder = new SampleBuilder(this);
	final PositionControl control = new PositionControl(this);
	
	final JLabel statusField = new JLabel();

	final Mat sampleMat = new Mat();// new Mat(size, CV_8UC3);
	final Rect roi = new Rect(213, 160, 213, 160);
	final Mat displayMat = new Mat();

	public ShotAssistant() {
		super("gmv.ShotAssistant");
		initFrame();
	}

	protected void initFrame() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(statusField, CENTER);
		p.add(newButton("Снимок", this::saveSample), EAST);
		frame.add(p, SOUTH);
		frame.validate();
	}

	@Override
	protected void processFrame() {
		boolean sm = !sampleMat.empty();
		bitwise_or(inputMat, sm ? sampleMat : inputMat, displayMat);
		boolean cr = sm && control.check(inputMat);;
		rectangle(displayMat, roi, cr ? Scalar.GREEN : Scalar.BLUE);
		if (writer.isOpened()) writer.write(displayMat);
		frame.showImage(matConverter.convert(displayMat));
	}

	void saveSample() {
		Mat m = sampleBuilder.build(inputMat);
		control.setSimple(m);
		cvtColor(m, sampleMat, COLOR_GRAY2BGR);
	}

	public static void main(String[] args) throws Exception {
		try (ShotAssistant app = new ShotAssistant()) {
			app.start();
		}
	}

}
