package com.gitlab.daring.image.assistant;

import com.gitlab.daring.image.video.BaseVideoProcessor;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Size;

import javax.swing.*;
import java.awt.BorderLayout;

import static com.gitlab.daring.image.MainContext.mainContext;
import static com.gitlab.daring.image.swing.SwingUtils.newButton;
import static com.gitlab.daring.image.util.ImageUtils.flipMat;
import static java.awt.BorderLayout.*;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_GRAY2BGR;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

class ShotAssistant extends BaseVideoProcessor {

	final boolean flipInput = config.getBoolean("flipInput");

	final TemplateBuilder templateBuilder = new TemplateBuilder(this);
	final PositionControl control = new PositionControl(this);
	final DisplayBuilder displayBuilder = new DisplayBuilder(this);
	final ConfigPanel configPanel = new ConfigPanel(this);

	final Mat sampleMat = new Mat();
	final Mat templateMat = new Mat();
	final Mat displayMat = new Mat();

	final JLabel statusField = new JLabel();
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
		if (flipInput)
			flipMat(inputMat, 1);
		checkResult = control.check(inputMat);
		displayBuilder.build(inputMat);
		if (writer.isOpened()) writer.write(displayMat);
		showImage(displayMat);
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

	@Override
	public void close() throws Exception {
		super.close();
		mainContext().close();
	}

	public static void main(String[] args) throws Exception {
		try (ShotAssistant app = new ShotAssistant()) {
			app.start();
		}
	}

}
