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
import one.util.streamex.StreamEx;
import org.bytedeco.javacpp.opencv_core.Mat;

import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;
import java.util.List;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.ImageUtils.cropMat;
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

	public void check() {
		env = script.env;

		loadMats();
		List<MatchResult> mrs = findMatches();

		//TODO use getAffineTransform

		Mat dm2 = diffBuilder.build(mrs.get(0).rect);
		showMat(dm2, "Различия");
	}

	void loadMats() {
		m1 = loadMat(sampleFile.v, "m1");
		m2 = loadMat(targetFile.v, "m2");
		objRect = getCenterRect(toJava(m2.size()), objSize.v * 0.01);
		tm1 = cropMat(m1, objRect);
	}

	Mat loadMat(String file, String name) {
		Mat m = script.runCommand("read", file, "grey");
		env.putMat(name, m);
		return m;
	}

	List<MatchResult> findMatches() {
		return StreamEx.of(1.0, 0.6, 0.3).map(this::findMatch).toList();
	}

	MatchResult findMatch(double size) {
		Rectangle r = getCenterRect(objRect, size);
		return matcher.findBest(m2, cropMat(m1, r));
	}

	void showMat(Mat m, String title) {
		script.env.mat = m;
		script.runCommand("show", title);
	}

}