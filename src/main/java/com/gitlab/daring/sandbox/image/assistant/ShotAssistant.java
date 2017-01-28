package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.video.BaseVideoProcessor;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatExpr;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacv.CanvasFrame;

import javax.swing.*;
import java.awt.BorderLayout;

import static com.gitlab.daring.sandbox.image.util.ImageUtils.addWeightedMat;
import static com.gitlab.daring.sandbox.image.util.SwingUtils.newButton;
import static java.awt.BorderLayout.*;
import static org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.BLUE;
import static org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.GREEN;
import static org.bytedeco.javacpp.opencv_core.bitwise_or;
import static org.bytedeco.javacpp.opencv_core.multiply;
import static org.bytedeco.javacpp.opencv_imgproc.*;

class ShotAssistant extends BaseVideoProcessor {

	final TemplateBuilder templateBuilder = new TemplateBuilder(this);
	final PositionControl control = new PositionControl(this);
	final ConfigPanel configPanel = new ConfigPanel(this);

	final Mat sampleMat = new Mat();
	final Mat templateMat = new Mat();
	final Mat displayMat = new Mat();

	final JLabel statusField = new JLabel();
	double sampleOpacity;
	double templateOpacity;
	boolean checkResult;

	public ShotAssistant() {
		super("gmv.ShotAssistant");
		initFrame();
		configPanel.showFrame();
	}

	private void initFrame() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(statusField, CENTER);
		p.add(newButton("Снимок", this::saveSample), EAST);
		frame.add(p, SOUTH);
		frame.validate();
	}

	@Override
	protected void processFrame() {
		checkResult = control.check(inputMat);
		buildDisplayMat();
		if (writer.isOpened()) writer.write(displayMat);
		frame.showImage(matConverter.convert(displayMat));
	}

	void buildDisplayMat() {
		Mat dm = displayMat;
		inputMat.copyTo(dm);
		if (!templateMat.empty()) {
			addWeightedMat(dm, sampleMat, dm, sampleOpacity);
			MatExpr dt = multiply(templateMat, templateOpacity);
			bitwise_or(dm, dt.asMat(), dm);
		} else {
			inputMat.copyTo(dm);
		}
		rectangle(dm, control.roi, checkResult ? GREEN : BLUE);
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

	CanvasFrame getFrame() {
		return frame;
	}

	public static void main(String[] args) throws Exception {
		try (ShotAssistant app = new ShotAssistant()) {
			app.start();
		}
	}

}
