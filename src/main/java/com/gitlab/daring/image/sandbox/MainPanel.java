package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandScriptPanel;
import com.gitlab.daring.image.command.parameter.FileParam;
import com.gitlab.daring.image.command.parameter.StringParam;

class MainPanel extends CommandScriptPanel {

    final ImageSandbox sb;
    final StringParam filesParam;

    MainPanel(ImageSandbox sb) {
        this.sb = sb;
        filesParam = createFilesParam();
        setScript(sb.config.getString("script"));
    }

    StringParam createFilesParam() {
        FileParam p = new FileParam(":Файлы:0").bind(sb.config, "files");
        addStaticParams(p);
        return p;
    }

}