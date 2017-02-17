package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.CommandScript;
import com.gitlab.daring.image.command.parameter.CommandParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.command.parameter.StringParam;
import com.gitlab.daring.image.common.BaseComponent;
import com.gitlab.daring.image.template.MatchResult;
import com.gitlab.daring.image.template.TemplateMatcher;
import com.typesafe.config.Config;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

import static com.gitlab.daring.image.command.CommandScriptUtils.runCommand;
import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;
import static java.util.Arrays.asList;
import static org.bytedeco.javacpp.opencv_core.LINE_8;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

@NotThreadSafe
class SealCheckService extends BaseComponent {

	final StringParam sampleFile = newStringParam("sampleFile", "Образец");
	final StringParam targetFile = newStringParam("targetFile", "Снимок");
	final IntParam objSize = new IntParam("0:Размер объекта:0-100").bind(config, "objSize");

	final TemplateMatcher matcher = new TemplateMatcher(getConfig("matcher"));
	final DiffBuilder diffBuilder = new DiffBuilder(this);

	CommandScript script;
	CommandEnv env;

	public SealCheckService(Config c) {
		super(c);
	}

	private StringParam newStringParam(String path, String name) {
		return new StringParam(":" + name).bind(config, path);
	}

	List<CommandParam<?>> getParams() {
		return asList(sampleFile, targetFile, objSize, diffBuilder.winSize);
	}

	public void setScript(CommandScript script) {
		this.script = script;
	}

	//TODO refactor

	public void check() {
		env = script.env;

		Mat m1 = loadMat(sampleFile.v, 1);
		Mat m2 = loadMat(targetFile.v, 2);

		Rect cr1 = getCenterRect(m1.size(), objSize.v * 0.01);
		Mat cm = m1.apply(cr1);
		MatchResult mr = matcher.findBest(m2, cm);

		//TODO use getAffineTransform

		Mat dm1 = diffBuilder.build(cm, m2.apply(cr1));
		Rect cr2 = new Rect(toOpencv(mr.point), cr1.size());
		Mat dm2 = diffBuilder.build(cm, m2.apply(cr2));

		rectangle(m1, cr1, Scalar.WHITE, 3, LINE_8, 0);
		showMat(m1, "Образец");
		rectangle(m2, cr2, Scalar.WHITE, 3, LINE_8, 0);
		showMat(m2, "Снимок");
		showMat(dm1, "Difference");
		showMat(dm2, "Различия");
	}

	Mat loadMat(String file, int i) {
		return runCommand(env, "read", file, "grey");
	}

	void showMat(Mat m, String title) {
		script.env.mat = m;
		runCommand(env, "show", title);
	}

}