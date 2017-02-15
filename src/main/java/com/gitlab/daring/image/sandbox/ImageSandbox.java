package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.common.BaseComponent;
import com.gitlab.daring.image.swing.BaseFrame;
import com.typesafe.config.Config;

import java.util.HashMap;
import java.util.Map;

import static com.gitlab.daring.image.MainContext.mainContext;
import static com.gitlab.daring.image.config.ConfigUtils.configFromMap;
import static com.gitlab.daring.image.config.ConfigUtils.saveDiffConfig;

public class ImageSandbox extends BaseComponent implements AutoCloseable {

	static final String ConfigPath = "gmv.ImageSandbox";

	final MainPanel mp = new MainPanel(this);
	final ScriptExecutor scriptExecutor = new ScriptExecutor(this);

	public ImageSandbox() {
		super(ConfigPath);
		mp.applyEvent.onFire(this::apply);
		mp.changeEvent.onFire(this::executeScript);
		executeScript();
	}

	void showFrame() {
		BaseFrame frame = new BaseFrame("ImageSandbox", mp);
		frame.addCloseListener(this::close);
		frame.show(800, 600);
	}

	void apply() {
		executeScript();
		saveConfig();
	}

	void executeScript() {
		scriptExecutor.executeAsync();
	}

	void saveConfig() {
		Map<String, Object> m = new HashMap<>();
		m.put("script", mp.script.getText());
		m.put("files", mp.filesParam.v);
		Config c = configFromMap(m).atPath(ConfigPath);
		saveDiffConfig(c, "conf/application.conf");
	}

	@Override
	public void close() {
		scriptExecutor.close();
		mainContext().close();
	}

	public static void main(String[] args) {
		new ImageSandbox().showFrame();
	}

}
