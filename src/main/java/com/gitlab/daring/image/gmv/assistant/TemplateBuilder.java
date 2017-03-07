package com.gitlab.daring.image.gmv.assistant;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;

import javax.annotation.concurrent.NotThreadSafe;

import static com.gitlab.daring.image.command.CommandUtils.EmptyCommand;

@NotThreadSafe
class TemplateBuilder extends BaseComponent {

    Command buildCmd = EmptyCommand;

    TemplateBuilder(ShotAssistant a) {
        super(a.config.getConfig("template"));
    }

    Mat build(Mat inputMat) {
        CommandEnv env = new CommandEnv();
        inputMat.copyTo(env.mat);
        buildCmd.execute(env);
        return env.mat;
    }

}
