package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.common.BaseComponent;
import com.gitlab.daring.image.concurrent.TaskExecutor;
import com.gitlab.daring.image.swing.BaseFrame;
import com.typesafe.config.Config;

import static com.gitlab.daring.image.MainContext.mainContext;

public class SealCheckSandbox extends BaseComponent implements AutoCloseable {

    static final String ConfigPath = "isb.SealCheckSandbox";

    final SealCheckService service = new SealCheckService(config);
    final MainPanel mp = new MainPanel(this);
    final TaskExecutor taskExec = new TaskExecutor();

    BaseFrame frame;

    public SealCheckSandbox() {
        super(ConfigPath);
        service.setScript(mp.script);
        mp.applyEvent.onFire(this::apply);
        mp.changeEvent.onFire(this::runCheck);
    }

    //TODO refactor
    public void apply(Config c) {
        showFrame();
        service.params = new SealCheckParams(c.withFallback(config));
        mp.updateParams();
        mp.applyEvent.fire();
    }

    void showFrame() {
        if (frame != null) return;
        frame = new BaseFrame("SealCheckSandbox", mp);
        frame.addCloseListener(this::close);
        frame.show(800, 600);
    }

    void apply() {
        runCheck();
        saveConfig();
    }

    void runCheck() {
        taskExec.executeAsync(service::check);
    }

    void saveConfig() {
        mp.saveConfig(ConfigPath);
    }

    @Override
    public void close() {
        taskExec.close();
        mainContext().close();
    }

    public static void main(String[] args) {
        new SealCheckSandbox().showFrame();
    }

}
