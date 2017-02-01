package com.gitlab.daring.image.assistant;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.CompositeCommand;
import com.gitlab.daring.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;

import javax.annotation.concurrent.NotThreadSafe;

import static com.gitlab.daring.image.command.CommandRegistry.parseCmdScript;

@NotThreadSafe
class TemplateBuilder extends BaseComponent {

	String script;
	CompositeCommand buildCmd ;

	TemplateBuilder(ShotAssistant a) {
		super(a.config.getConfig("template"));
		setScript(config.getString("script"));
	}

	void setScript(String script) {
//		closeQuietly(buildCmd);
		this.script = script;
		buildCmd = parseCmdScript(script);
	}

	Mat build(Mat inputMat) {
		CommandEnv env = new CommandEnv();
		inputMat.copyTo(env.mat);
		buildCmd.execute(env);
		return env.mat;
	}

}
