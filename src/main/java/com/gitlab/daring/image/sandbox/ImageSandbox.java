package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.CompositeCommand;
import com.gitlab.daring.image.common.BaseComponent;
import com.typesafe.config.Config;

import java.util.HashMap;
import java.util.Map;

import static com.gitlab.daring.image.command.CommandRegistry.parseCmdScript;
import static com.gitlab.daring.image.config.ConfigUtils.configFromMap;
import static com.gitlab.daring.image.config.ConfigUtils.saveDiffConfig;
import static com.gitlab.daring.image.util.CommonUtils.closeQuietly;

public class ImageSandbox extends BaseComponent {

	static final String ConfigPath = "gmv.ImageSandbox";

	final ManPanel mainPanel = new ManPanel(this);
	final CommandEnv cmdEnv = new CommandEnv();

	String script;
	CompositeCommand scriptCmd;

	public ImageSandbox() {
		super(ConfigPath);
		mainPanel.applyEvent.onFire(this::saveConfig);
		mainPanel.showFrame();
	}

	void setScript(String script) {
		closeQuietly(scriptCmd);
		this.script = script;
		scriptCmd = parseCmdScript(script);
		scriptCmd.execute(cmdEnv);
	}

	void saveConfig() {
		Map<String, Object> m = new HashMap<>();
		m.put("script", script);
		Config c = configFromMap(m).atPath(ConfigPath);
		saveDiffConfig(c, "conf/application.conf");
	}

	public static void main(String[] args) {
		new ImageSandbox();
	}

}
