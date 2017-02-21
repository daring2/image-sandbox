package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandEnv;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;

import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.gitlab.daring.image.util.ImageUtils.cropMat;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static one.util.streamex.IntStreamEx.range;
import static org.bytedeco.javacpp.opencv_core.min;

@NotThreadSafe
class DiffBuilder {

	final CheckTask task;
	final SealCheckService srv;

	CommandEnv env;
	int wo;
	Mat dm1, dm2;

	public DiffBuilder(CheckTask task) {
		this.task = task;
		srv = task.srv;
	}

	Mat build(Rectangle mr) {
		env = task.script.env;
		wo = srv.winOffset.v;
		dm1 = runPreDiff(task.tm1);
		Mat m2 = buildMat2(mr);
		dm2 = runPreDiff(m2);
		return runBuildDiff();
	}

	private Mat buildMat2(Rectangle mr) {
		Rectangle r = new Rectangle(mr);
		r.translate(-wo / 2, -wo / 2);
		return cropMat(task.m2, r);
	}

	Mat runPreDiff(Mat m) {
		return task.script.runTask("preDiff", m);
	}

	Mat runBuildDiff() {
		Dimension d = toJava(dm1.size());
		Rectangle wr = new Rectangle(0, 0, d.width - wo, d.height - wo);
		env.putMat("dm1", cropMat(dm1, wr));
		List<Mat> dms = new ArrayList<>();
		range(wo + 1).forEach(x -> range(wo + 1).forEach(y -> {
			wr.setLocation(x, y);
			env.putMat("dm2", cropMat(dm2, wr));
			task.script.runTask("buildDiff");
			dms.add(env.mat.clone());
		}));
		Mat rm = new Mat(wr.height, wr.width, dm1.type(), Scalar.WHITE);
		dms.forEach(m -> min(rm, m, rm));
		env.putMat("dr", rm);
		return task.script.runTask("postDiff", rm);
	}

}