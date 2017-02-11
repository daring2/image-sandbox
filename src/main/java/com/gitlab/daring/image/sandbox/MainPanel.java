package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandScriptPanel;
import com.gitlab.daring.image.swing.BaseFrame;

import javax.swing.*;

class MainPanel extends CommandScriptPanel {

	final ImageSandbox sb;

	MainPanel(ImageSandbox sb) {
		this.sb = sb;
	}

	JFrame showFrame() {
		BaseFrame frame = new BaseFrame("ImageSandbox", this);
		frame.setSize(800, 600);
		frame.setVisible(true);
		return frame;
	}

}
