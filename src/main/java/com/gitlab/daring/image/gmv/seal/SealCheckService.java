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
import java.util.ArrayList;
import java.util.List;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.ImageUtils.*;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static java.util.Arrays.asList;
import static org.bytedeco.javacpp.opencv_imgproc.getAffineTransform;
import static org.bytedeco.javacpp.opencv_imgproc.warpAffine;

@NotThreadSafe
class SealCheckService extends BaseComponent {

	final StringParam sampleFile = newStringParam("sampleFile", "Образец");
	final StringParam targetFile = newStringParam("targetFile", "Снимок");
	final IntParam objSize = new IntParam("0:Размер объекта:0-100").bind(config, "objSize");

	final TemplateMatcher matcher = new TemplateMatcher(getConfig("matcher"));
	final DiffBuilder diffBuilder = new DiffBuilder(this);

	//TODO extract CheckTask

	CommandScript script;
	CommandEnv env;

	Mat m1, m2, tm1;
	Rectangle objRect;
	List<MatchResult> mrs;
	List<Point> ps1, ps2;

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
		findMatches();
		Mat m3 = transformTarget();

		Mat dm2 = diffBuilder.build(mrs.get(0).rect);
		showMat(dm2, "Различия");

		// debug
		showMat(tm1, "tm1");
		showMat(cropMat(m2, objRect), "tm2");
		showMat(cropMat(m3, objRect), "tm3");
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

	void findMatches() {
		mrs = new ArrayList<>();
		ps1 = new ArrayList<>();
		ps2 = new ArrayList<>();
		Rectangle r = new Rectangle(objRect);
		findMatch(r);
		r.setSize(r.width / 3, r.height / 3);
		r.translate(r.width, 0); findMatch(r);
		r.translate(0, r.height); findMatch(r);
	}

	void findMatch(Rectangle r) {
		MatchResult mr = matcher.findBest(m2, cropMat(m1, r));
		mrs.add(mr);
		ps1.add(r.getLocation());
		ps2.add(mr.point.getLocation());
		// debug
		drawRect(m1, r, Scalar.WHITE, 1);
		drawRect(m2, mr.rect, Scalar.WHITE, 1);
	}

	Mat transformTarget() {
		Mat tm = getAffineTransform(newPointArray(ps1), newPointArray(ps2));
		System.out.println("affine transform: " + tm.createIndexer().toString());
		return buildMat(r -> warpAffine(m2, r, tm, m2.size()));
	}

	void showMat(Mat m, String title) {
		script.env.mat = m;
		script.runCommand("show", title);
	}

}