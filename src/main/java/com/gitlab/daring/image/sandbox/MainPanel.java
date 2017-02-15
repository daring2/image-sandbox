package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandScriptPanel;
import com.gitlab.daring.image.command.parameter.StringParam;
import com.gitlab.daring.image.swing.BaseFrame;

import javax.swing.*;

class MainPanel extends CommandScriptPanel {

	final ImageSandbox sb;
	final StringParam filesParam;

	MainPanel(ImageSandbox sb) {
		this.sb = sb;
		filesParam = createFilesParam();
	}

	StringParam createFilesParam() {
		String sv = sb.config.getString("files") + ":Файлы";
		return addStaticParam(new StringParam(sv));
	}

	JFrame showFrame() {
		BaseFrame frame = new BaseFrame("ImageSandbox", this);
		frame.setSize(800, 600);
		frame.setVisible(true);
		return frame;
	}

}
