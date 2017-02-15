package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.CommandScriptPanel;
import com.gitlab.daring.image.common.BaseComponent;
import com.gitlab.daring.image.concurrent.TaskExecutor;
import com.gitlab.daring.image.swing.BaseFrame;
import com.typesafe.config.Config;

import java.util.HashMap;
import java.util.Map;

import static com.gitlab.daring.image.MainContext.mainContext;
import static com.gitlab.daring.image.config.ConfigUtils.configFromMap;
import static com.gitlab.daring.image.config.ConfigUtils.saveDiffConfig;
import static com.gitlab.daring.image.swing.SwingUtils.addWindowClosedListener;

public class CheckSealSandbox extends BaseComponent implements AutoCloseable {

	static final String ConfigPath = "gmv.CheckSealSandbox";

	final CommandScriptPanel sp = new CommandScriptPanel();
	final TaskExecutor executor = new TaskExecutor();

	public CheckSealSandbox() {
		super(ConfigPath);
		sp.applyEvent.onFire(this::apply);
		sp.changeEvent.onFire(this::executeScript);
		sp.setScript(config.getString("script"));
	}

	void showFrame() {
		BaseFrame frame = new BaseFrame("CheckSealSandbox", sp);
		frame.setSize(800, 600);
		frame.setVisible(true);
		addWindowClosedListener(frame, this::close);
	}

	void apply() {
		executeScript();
		saveConfig();
	}

	void executeScript() {
		executor.executeAsync(sp.script::execute);
	}

	void saveConfig() {
		Map<String, Object> m = new HashMap<>();
		m.put("script", sp.script.getText());
		Config c = configFromMap(m).atPath(ConfigPath);
		saveDiffConfig(c, "conf/application.conf");
	}

	@Override
	public void close() {
		mainContext().close();
	}

	public static void main(String[] args) {
		CommandRegistry cr = CommandRegistry.Instance;
		cr.register("checkSeal", CheckSealCommand::new);
		new CheckSealSandbox().showFrame();
	}

}
