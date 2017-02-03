package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandScriptPanel;
import com.gitlab.daring.image.swing.BaseFrame;

class MainPanel extends CommandScriptPanel {

	final ImageSandbox sb;

	MainPanel(ImageSandbox sb) {
		this.sb = sb;
	}

	void showFrame() {
		BaseFrame frame = new BaseFrame("ImageSandbox", this);
		frame.setSize(640, 480);
		frame.setVisible(true);
	}

}
