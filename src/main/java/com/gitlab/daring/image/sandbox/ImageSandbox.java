package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.common.BaseComponent;
import com.typesafe.config.Config;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static com.gitlab.daring.image.MainContext.mainContext;
import static com.gitlab.daring.image.config.ConfigUtils.configFromMap;
import static com.gitlab.daring.image.config.ConfigUtils.saveDiffConfig;
import static com.gitlab.daring.image.swing.SwingUtils.addWindowClosedListener;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class ImageSandbox extends BaseComponent implements AutoCloseable {

	static final String ConfigPath = "gmv.ImageSandbox";

	final MainPanel mp = new MainPanel(this);
	final JFrame frame = mp.showFrame();
	final ExecutorService executor = newSingleThreadExecutor();

	public ImageSandbox() {
		super(ConfigPath);
		mp.applyEvent.onFire(this::apply);
		mp.changeEvent.onFire(this::executeScript);
		mp.setScript(config.getString("script"));
		addWindowClosedListener(frame, this::close);
	}

	void apply() {
		executeScript();
		saveConfig();
	}

	void executeScript() {
		mp.script.executeAsync(executor);
	}

	void saveConfig() {
		Map<String, Object> m = new HashMap<>();
		m.put("script", mp.script.getText());
		Config c = configFromMap(m).atPath(ConfigPath);
		saveDiffConfig(c, "conf/application.conf");
	}

	@Override
	public void close() {
		frame.dispose();
		executor.shutdownNow();
		mainContext().close();
	}

	public static void main(String[] args) {
		new ImageSandbox();
	}

}
