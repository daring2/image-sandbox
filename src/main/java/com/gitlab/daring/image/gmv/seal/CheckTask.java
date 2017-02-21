package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandScript;
import com.gitlab.daring.image.template.MatchResult;
import org.bytedeco.javacpp.opencv_core.Mat;

import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.ImageUtils.*;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static org.bytedeco.javacpp.opencv_imgproc.getAffineTransform;
import static org.bytedeco.javacpp.opencv_imgproc.warpAffine;

@NotThreadSafe
class CheckTask {

	final SealCheckService srv;
	final CommandScript script;

	final Mat m1, m2;
	final Rectangle objRect;

	final List<MatchResult> mrs = new ArrayList<>();
	final List<Point> ps1 = new ArrayList<>();
	final List<Point> ps2 = new ArrayList<>();

	CheckTask(SealCheckService srv) {
		this.srv = srv;
		script = srv.script;
		m1 = loadMat(srv.sampleFile.v, "m1");
		m2 = loadMat(srv.targetFile.v, "m2");
		objRect = getCenterRect(toJava(m2.size()), srv.objSize.v * 0.01);
	}

	Mat loadMat(String file, String name) {
		Mat m = script.runCommand("read", file, "grey");
		script.env.putMat(name, m);
		return m;
	}

	void run() {
		findMatches();
		Mat tm2 = transformTarget();
		Mat dm = new DiffBuilder(this).build(tm2);
		showMat(dm, "Различия");
		// debug
//		showMat(cropMat(m1, objRect), "cm1");
//		showMat(cropMat(m2, objRect), "cm2");
//		showMat(cropMat(tm2, objRect), "cm3");
	}

	void findMatches() {
		Rectangle r = new Rectangle(objRect);
		findMatch(r);
		r.setSize(r.width / 3, r.height / 3);
		r.translate(r.width, 0); findMatch(r);
		r.translate(0, r.height); findMatch(r);
	}

	void findMatch(Rectangle r) {
		MatchResult mr = srv.matcher.findBest(m2, cropMat(m1, r));
		mrs.add(mr);
		ps1.add(r.getLocation());
		ps2.add(mr.point.getLocation());
		// debug
//		drawRect(m1, r, Scalar.WHITE, 1);
//		drawRect(m2, mr.rect, Scalar.WHITE, 1);
	}

	Mat transformTarget() {
		Mat tm = getAffineTransform(newPointArray(ps2), newPointArray(ps1));
		return buildMat(r -> warpAffine(m2, r, tm, m2.size()));
	}

	void showMat(Mat m, String title) {
		script.env.mat = m;
		script.runCommand("show", title);
	}

}