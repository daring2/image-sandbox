package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandScript;
import com.gitlab.daring.image.common.BaseComponent;
import com.gitlab.daring.image.template.TemplateMatcher;
import com.typesafe.config.Config;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
class SealCheckService extends BaseComponent {

    final TemplateMatcher matcher = new TemplateMatcher(getConfig("matcher"));

    SealCheckParams params;
    CommandScript script;

    SealCheckService(Config c) {
        super(c);
        params = new SealCheckParams(c);
    }

    void setScript(CommandScript script) {
        this.script = script;
    }

    void check() {
        new CheckTask(this).run();
    }

}