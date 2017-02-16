package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandScriptPanel;

class MainPanel extends CommandScriptPanel {

	final SealCheckSandbox cb;
	final SealCheckService srv;

	public MainPanel(SealCheckSandbox cb) {
		this.cb = cb;
		this.srv = cb.service;
		addStaticParams(srv.sampleFile, srv.targetFile, srv.objSize);
		setScript(cb.config.getString("script"));
	}

}