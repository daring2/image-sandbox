package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.video.BaseVideoProcessor;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import javax.swing.*;
import java.awt.BorderLayout;
import static com.gitlab.daring.sandbox.image.util.SwingUtils.newButton;
import static java.awt.BorderLayout.*;
import static org.bytedeco.javacpp.opencv_core.bitwise_or;
import static org.bytedeco.javacpp.opencv_imgproc.*;

class ShotAssistant extends BaseVideoProcessor {

	final TemplateBuilder templateBuilder = new TemplateBuilder(this);
	final PositionControl control = new PositionControl(this);

	final Mat sampleMat = new Mat();
	final Mat templateMat = new Mat();
	final Mat displayMat = new Mat();

	final JLabel statusField = new JLabel();

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
		boolean tm = !templateMat.empty();
		bitwise_or(inputMat, tm ? templateMat : inputMat, displayMat);
		boolean cr = tm && control.check(inputMat);
		rectangle(displayMat, control.roi, cr ? Scalar.GREEN : Scalar.BLUE);
		if (writer.isOpened()) writer.write(displayMat);
		frame.showImage(matConverter.convert(displayMat));
	}

	void saveSample() {
		inputMat.copyTo(sampleMat);
		Mat m = templateBuilder.build(sampleMat);
		control.setTemplate(m);
		cvtColor(m, templateMat, COLOR_GRAY2BGR);
	}

	Size getSize() {
		return size;
	}

	public static void main(String[] args) throws Exception {
		try (ShotAssistant app = new ShotAssistant()) {
			app.start();
		}
	}

}
