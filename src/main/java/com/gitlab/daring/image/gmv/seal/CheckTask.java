package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.CommandScript;
import org.bytedeco.javacpp.opencv_core.Mat;

import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.ImageUtils.buildMat;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static org.bytedeco.javacpp.opencv_imgproc.warpAffine;

@NotThreadSafe
class CheckTask {

	final SealCheckService srv;
	final CommandScript script;
	final CommandEnv env;
	final Mat m1, m2;
	final Rectangle objRect;

	CheckTask(SealCheckService srv) {
		this.srv = srv;
		script = srv.script;
		env = script.env;
		m1 = loadMat(srv.sampleFile.v, "m1");
		m2 = loadMat(srv.targetFile.v, "m2");
		objRect = getCenterRect(toJava(m2.size()), srv.objSize.pv());
	}

	Mat loadMat(String file, String name) {
		env.vars.put("file", file);
		env.vars.put("name", name);
		Mat m = script.runTask("load", env.mat);
		env.putMat(name, m);
		return m;
	}

	void run() {
		Mat tm = new TransformBuilder(this).build();
		Mat tm2 = transformTarget(tm);
		Mat dm = new DiffBuilder(this).build(tm2);
		showMat(dm, "Различия");
	}

	Mat transformTarget(Mat tm) {
//		System.out.println("tm = " + tm.createIndexer());
		return buildMat(r -> warpAffine(m2, r, tm, m2.size()));
	}

	void showMat(Mat m, String title) {
		env.mat = m;
		script.runCommand("show", title);
	}

}