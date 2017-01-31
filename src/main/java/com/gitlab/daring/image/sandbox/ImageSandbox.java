package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.CompositeCommand;
import com.gitlab.daring.image.common.BaseComponent;

import static com.gitlab.daring.image.command.CommandRegistry.parseScript;
import static com.gitlab.daring.image.util.CommonUtils.closeQuietly;

public class ImageSandbox extends BaseComponent {

	final ManPanel mainPanel = new ManPanel(this);
	final CommandEnv cmdEnv = new CommandEnv();

	String script;
	CompositeCommand scriptCmd;

	public ImageSandbox() {
		super("gmv.ImageSandbox");
		mainPanel.showFrame();
		//TODO save config
	}

	void setScript(String script) {
		closeQuietly(scriptCmd);
		this.script = script;
		scriptCmd = parseScript(script);
		scriptCmd.execute(cmdEnv);
	}

	public static void main(String[] args) {
		new ImageSandbox();
	}

}
