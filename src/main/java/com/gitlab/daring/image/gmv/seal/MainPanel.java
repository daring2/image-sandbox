package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandScriptPanel;

class MainPanel extends CommandScriptPanel {

	final CheckSealSandbox cb;

	public MainPanel(CheckSealSandbox cb) {
		this.cb = cb;
		setScript(cb.config.getString("script"));
	}

}