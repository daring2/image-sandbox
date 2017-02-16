package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.common.BaseComponent;
import com.gitlab.daring.image.swing.BaseFrame;

import static com.gitlab.daring.image.MainContext.mainContext;
import static com.gitlab.daring.image.sandbox.SandboxUtils.saveCompConfig;

public class SealCheckSandbox extends BaseComponent implements AutoCloseable {

	static final String ConfigPath = "gmv.SealCheckSandbox";

	final CheckSealService service = new CheckSealService(config);
	final MainPanel sp = new MainPanel(this);

	public SealCheckSandbox() {
		super(ConfigPath);
		sp.applyEvent.onFire(this::apply);
		sp.changeEvent.onFire(this::runCheck);
	}

	void showFrame() {
		BaseFrame frame = new BaseFrame("SealCheckSandbox", sp);
		frame.addCloseListener(this::close);
		frame.show(800, 600);
	}

	void apply() {
		runCheck();
		saveConfig();
	}

	void runCheck() {
		service.check();
	}

	void saveConfig() {
		saveCompConfig(ConfigPath, m -> {
			m.put("sampleFile", service.sampleFile.v);
			m.put("targetFile", service.targetFile.v);
			m.put("objSize", service.objSize.v);
			m.put("script", sp.script.getText());
		});
	}

	@Override
	public void close() {
		mainContext().close();
	}

	public static void main(String[] args) {
		new SealCheckSandbox().showFrame();
	}

}
