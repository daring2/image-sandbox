package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandScriptPanel;
import com.gitlab.daring.image.command.parameter.StringParam;

class MainPanel extends CommandScriptPanel {

	final ImageSandbox sb;
	final StringParam filesParam;

	MainPanel(ImageSandbox sb) {
		this.sb = sb;
		filesParam = createFilesParam();
		setScript(sb.config.getString("script"));
	}

	StringParam createFilesParam() {
		return addStaticParam(new StringParam(":Файлы").bind(sb.config, "files"));
	}

}
