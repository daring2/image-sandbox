package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.common.BaseComponent;
import com.gitlab.daring.image.swing.BaseFrame;

import static com.gitlab.daring.image.MainContext.mainContext;

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
		sp.saveConfig(ConfigPath);
	}

	@Override
	public void close() {
		mainContext().close();
	}

	public static void main(String[] args) {
		new SealCheckSandbox().showFrame();
	}

}
