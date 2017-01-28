package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.command.Command;
import com.gitlab.daring.sandbox.image.command.CommandEnv;
import com.gitlab.daring.sandbox.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;

import javax.annotation.concurrent.NotThreadSafe;

import static com.gitlab.daring.sandbox.image.command.CommandRegistry.parseScript;
import static com.gitlab.daring.sandbox.image.util.ImageUtils.convertToGrey;

@NotThreadSafe
class TemplateBuilder extends BaseComponent {

	Command buildCmd ;

	TemplateBuilder(ShotAssistant a) {
		super(a.config.getConfig("template"));
		setScript(config.getString("script"));
	}

	void setScript(String script) {
		buildCmd = parseScript(script);
	}

	Mat build(Mat inputMat) {
		CommandEnv env = new CommandEnv();
		convertToGrey(inputMat, env.mat);
		buildCmd.execute(env);
		return env.mat;
	}

}
