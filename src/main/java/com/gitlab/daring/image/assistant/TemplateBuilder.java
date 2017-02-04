package com.gitlab.daring.image.assistant;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;

import javax.annotation.concurrent.NotThreadSafe;

import static com.gitlab.daring.image.command.CommandUtils.EmptyCommand;

@NotThreadSafe
class TemplateBuilder extends BaseComponent {

	final CommandEnv cmdEnv = new CommandEnv();
	Command buildCmd = EmptyCommand;

	TemplateBuilder(ShotAssistant a) {
		super(a.config.getConfig("template"));
	}

	Mat build(Mat inputMat) {
		inputMat.copyTo(cmdEnv.mat);
		buildCmd.execute(cmdEnv);
		return cmdEnv.mat;
	}

}
