package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.commands.Command;
import com.gitlab.daring.sandbox.image.commands.CommandEnv;
import com.gitlab.daring.sandbox.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;

import javax.annotation.concurrent.NotThreadSafe;

import static com.gitlab.daring.sandbox.image.commands.CommandRegistry.parseScript;
import static com.gitlab.daring.sandbox.image.util.ImageUtils.convertToGrey;

@NotThreadSafe
class TemplateBuilder extends BaseComponent {

	final String buildScript = config.getString("buildScript");
	final Command buildCmd = parseScript(buildScript);

	TemplateBuilder(ShotAssistant a) {
		super(a.config.getConfig("template"));
	}

	Mat build(Mat inputMat) {
		CommandEnv env = new CommandEnv();
		convertToGrey(inputMat, env.mat);
		buildCmd.execute(env);
		return env.mat;
	}

}
