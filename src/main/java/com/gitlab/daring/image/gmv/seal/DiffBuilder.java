package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;

import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static one.util.streamex.IntStreamEx.range;
import static org.bytedeco.javacpp.opencv_core.min;

@NotThreadSafe
class DiffBuilder extends BaseComponent {

	final IntParam winSize = new IntParam("0:Смещение:0-10").bind(config, "winSize");
	final SealCheckService srv;

	Mat dm1, dm2;
	CommandEnv env;

	public DiffBuilder(SealCheckService srv) {
		super(srv.config.getConfig("diffBuilder"));
		this.srv = srv;
	}

	Mat build(Mat m1, Mat m2) {
		env = srv.env;
		dm1 = runPreDiff(m1);
		dm2 = runPreDiff(m2);
		return runBuildDiff();
	}

	Mat runPreDiff(Mat m) {
		return srv.script.runTask("preDiff", m);
	}

	Mat runBuildDiff() {
		//TODO simplify
		int w = winSize.v;
		Dimension d = new Dimension(dm1.cols() - w, dm1.rows() - w);
		Rect r1 = new Rect(0, 0, d.width, d.height);
		env.putMat("dm1", dm1.apply(r1));
		List<Mat> dms = new ArrayList<>();
		range(w + 1).forEach(x -> range(w + 1).forEach(y -> {
			Rect r2 = new Rect(x, y, d.width, d.height);
			env.putMat("dm2", dm2.apply(r2));
			srv.script.runTask("buildDiff");
			dms.add(env.mat.clone());
		}));
		Mat rm = new Mat(r1.size(), dm1.type(), Scalar.WHITE);
		dms.forEach(m -> min(rm, m, rm));
		return srv.script.runTask("postDiff", rm);
	}

}