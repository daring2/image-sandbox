package com.gitlab.daring.image.assistant;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;
import javax.annotation.concurrent.NotThreadSafe;
import static com.gitlab.daring.image.command.CommandRegistry.parseScript;

@NotThreadSafe
class TemplateBuilder extends BaseComponent {

	String script;
	Command buildCmd ;

	TemplateBuilder(ShotAssistant a) {
		super(a.config.getConfig("template"));
		setScript(config.getString("script"));
	}

	void setScript(String script) {
		this.script = script;
		buildCmd = parseScript(script);
	}

	Mat build(Mat inputMat) {
		CommandEnv env = new CommandEnv();
		inputMat.copyTo(env.mat);
		buildCmd.execute(env);
		return env.mat;
	}

}
