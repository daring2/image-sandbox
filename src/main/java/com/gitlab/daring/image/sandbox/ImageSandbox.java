package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.CompositeCommand;
import com.gitlab.daring.image.common.BaseComponent;
import com.typesafe.config.Config;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.gitlab.daring.image.MainContext.mainContext;
import static com.gitlab.daring.image.config.ConfigUtils.configFromMap;
import static com.gitlab.daring.image.config.ConfigUtils.saveDiffConfig;
import static com.gitlab.daring.image.swing.SwingUtils.addWindowClosedListener;

public class ImageSandbox extends BaseComponent {

	static final String ConfigPath = "gmv.ImageSandbox";

	final MainPanel mp = new MainPanel(this);
	final JFrame frame = mp.showFrame();
	final CommandEnv cmdEnv = new CommandEnv();
	final Consumer<Void> chaneListener = e -> execute();

	CompositeCommand scriptCmd;

	public ImageSandbox() {
		super(ConfigPath);
		mp.applyEvent.onFire(this::apply);
		mp.setScript(config.getString("script"));
		addWindowClosedListener(frame, () -> mainContext().close());
	}

	void apply() {
		scriptCmd = mp.getScriptCommand();
		scriptCmd.addParamChangeListener(chaneListener);
		execute();
		saveConfig();
	}

	void execute() {
		scriptCmd.execute(cmdEnv);
	}

	void saveConfig() {
		Map<String, Object> m = new HashMap<>();
		m.put("script", mp.getScript());
		Config c = configFromMap(m).atPath(ConfigPath);
		saveDiffConfig(c, "conf/application.conf");
	}

	public static void main(String[] args) {
		new ImageSandbox();
	}

}
