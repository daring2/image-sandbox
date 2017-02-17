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
import org.bytedeco.javacpp.opencv_core.Scalar;

import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;
import java.util.List;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.ImageUtils.cropMat;
import static com.gitlab.daring.image.util.ImageUtils.drawRect;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static java.util.Arrays.asList;

@NotThreadSafe
class SealCheckService extends BaseComponent {

	final StringParam sampleFile = newStringParam("sampleFile", "Образец");
	final StringParam targetFile = newStringParam("targetFile", "Снимок");
	final IntParam objSize = new IntParam("0:Размер объекта:0-100").bind(config, "objSize");

	final TemplateMatcher matcher = new TemplateMatcher(getConfig("matcher"));
	final DiffBuilder diffBuilder = new DiffBuilder(this);

	CommandScript script;
	CommandEnv env;
	Mat m1, m2, tm1;
	Rectangle objRect;

	public SealCheckService(Config c) {
		super(c);
	}

	private StringParam newStringParam(String path, String name) {
		return new StringParam(":" + name).bind(config, path);
	}

	List<CommandParam<?>> getParams() {
		return asList(sampleFile, targetFile, objSize, diffBuilder.winOffset);
	}

	public void setScript(CommandScript script) {
		this.script = script;
	}

	//TODO refactor

	public void check() {
		env = script.env;

		loadMats();
		MatchResult mr = findMatch();

		//TODO use getAffineTransform

		Mat dm1 = diffBuilder.build(objRect);
		Mat dm2 = diffBuilder.build(mr.rect);

		drawRect(m1, objRect, Scalar.WHITE, 3);
		showMat(m1, "Образец");
		drawRect(m2, mr.rect, Scalar.WHITE, 3);
		showMat(m2, "Снимок");
		showMat(dm1, "Difference");
		showMat(dm2, "Различия");
	}

	void loadMats() {
		m1 = loadMat(sampleFile.v);
		m2 = loadMat(targetFile.v);
		objRect = getCenterRect(toJava(m2.size()), objSize.v * 0.01);
	}

	Mat loadMat(String file) {
		return script.runCommand("read", file, "grey");
	}

	MatchResult findMatch() {
		tm1 = cropMat(m1, objRect);
		return matcher.findBest(m2, tm1);
	}

	void showMat(Mat m, String title) {
		script.env.mat = m;
		script.runCommand("show", title);
	}

}