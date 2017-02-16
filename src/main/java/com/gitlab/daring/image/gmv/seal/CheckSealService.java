package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.command.parameter.StringParam;
import com.gitlab.daring.image.common.BaseComponent;
import com.gitlab.daring.image.template.MatchResult;
import com.gitlab.daring.image.template.TemplateMatcher;
import com.typesafe.config.Config;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;

import static com.gitlab.daring.image.command.CommandScriptUtils.runCommand;
import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.ImageUtils.buildMat;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;
import static org.bytedeco.javacpp.opencv_core.LINE_8;
import static org.bytedeco.javacpp.opencv_core.absdiff;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

class CheckSealService extends BaseComponent {

	final StringParam sampleFile = newStringParam("sampleFile", "Образец");
	final StringParam targetFile = newStringParam("targetFile", "Снимок");
	final IntParam objSize = new IntParam(config.getInt("objSize") + ":Размер:0-100");

	final CommandEnv cmdEnv = new CommandEnv();
	final TemplateMatcher matcher = new TemplateMatcher(getConfig("matcher"));

	public CheckSealService(Config c) {
		super(c);
	}

	StringParam newStringParam(String path, String name) {
		return new StringParam(config.getString(path) + ":" + name);
	}

	//TODO refactor

	public void check() {
		CommandEnv.local.set(cmdEnv);

		Mat m1 = runCommand("read", sampleFile.v, "grey");
		Mat m2 = runCommand("read", targetFile.v, "grey");

		Rect cr1 = getCenterRect(m1.size(), objSize.v * 0.01);
		Mat cm = m1.apply(cr1);
		MatchResult mr = matcher.findBest(m2, cm);

		//TODO use getAffineTransform

		Mat dm1 = buildDiff(cm, m2.apply(cr1));
		Rect cr2 = new Rect(toOpencv(mr.point), cr1.size());
		Mat dm2 = buildDiff(cm, m2.apply(cr2));

		rectangle(m1, cr1, Scalar.WHITE, 3, LINE_8, 0);
		showMat(m1, "Sample");
		rectangle(m2, cr2, Scalar.WHITE, 3, LINE_8, 0);
		showMat(m2, "Image");
		showMat(dm1, "Difference");
		showMat(dm2, "Match");
	}

	Mat buildDiff(Mat m1, Mat m2) {
		return buildMat(r -> absdiff(blur(m1), blur(m2), r));
	}

	Mat blur(Mat m) {
		cmdEnv.mat = m;
		return runCommand("medianBlur", "2", "5");
	}

	void showMat(Mat m, String title) {
		cmdEnv.mat = m; runCommand("show", title);
	}

}