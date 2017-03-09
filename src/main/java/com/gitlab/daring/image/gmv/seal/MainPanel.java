package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandScriptPanel;

class MainPanel extends CommandScriptPanel {

    final SealCheckSandbox cb;

    public MainPanel(SealCheckSandbox cb) {
        this.cb = cb;
        staticParams.addAll(cb.service.getParams());
        setScript(cb.config.getString("script").trim());
    }

}