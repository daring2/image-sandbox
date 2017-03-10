package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandScriptPanel;

import static java.util.Collections.emptyList;

class MainPanel extends CommandScriptPanel {

    final SealCheckSandbox cb;

    public MainPanel(SealCheckSandbox cb) {
        this.cb = cb;
        updateParams();
        setScript(cb.config.getString("script").trim());
    }

    void updateParams() {
        staticParamPanel.setParams(emptyList());
        staticParams.clear();
        staticParams.addAll(cb.service.params.getParams());
    }

}