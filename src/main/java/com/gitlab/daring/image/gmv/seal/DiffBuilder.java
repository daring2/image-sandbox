package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;

class DiffBuilder extends BaseComponent {

	final IntParam winSize = new IntParam("0:Точность:0-10").bind(config, "winSize");
	final SealCheckService srv;

	public DiffBuilder(SealCheckService srv) {
		super(srv.config.getConfig("diffBuilder"));
		this.srv = srv;
	}

	Mat build(Mat m1, Mat m2) {
		runPreDiff(m1, 1);
		runPreDiff(m2, 2);
		srv.script.runTask("buildDiff");
		return srv.env.mat.clone();
	}

	void runPreDiff(Mat m, int i) {
		srv.env.mat = m.clone();
		srv.script.runTask("preDiff");
		srv.env.putMat("dm" + i, srv.env.mat);
	}

}