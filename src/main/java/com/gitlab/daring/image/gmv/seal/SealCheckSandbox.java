package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.common.BaseComponent;
import com.gitlab.daring.image.concurrent.TaskExecutor;
import com.gitlab.daring.image.swing.BaseFrame;
import com.typesafe.config.Config;

import static com.gitlab.daring.image.MainContext.mainContext;

public class SealCheckSandbox extends BaseComponent implements AutoCloseable {

    static final String ConfigPath = "isb.SealCheckSandbox";

    final SealCheckService service = new SealCheckService(config);
    final MainPanel sp = new MainPanel(this);
    final TaskExecutor taskExec = new TaskExecutor();

    BaseFrame frame;

    public SealCheckSandbox() {
        super(ConfigPath);
        service.setScript(sp.script);
        sp.applyEvent.onFire(this::apply);
        sp.changeEvent.onFire(this::runCheck);
    }

    //TODO refactor
    public void apply(Config c) {
        showFrame();
        service.sampleFile.v = c.getString("sampleFile");
        service.shotFile.v = c.getString("shotFile");
        service.objSize.v = c.getInt("objSize");
        runCheck();
    }

    void showFrame() {
        if (frame != null) return;
        frame = new BaseFrame("SealCheckSandbox", sp);
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
        sp.saveConfig(ConfigPath);
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
