package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.common.BaseComponent;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;

import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.gitlab.daring.image.util.ImageUtils.cropMat;
import static one.util.streamex.IntStreamEx.range;
import static org.bytedeco.javacpp.opencv_core.min;

@NotThreadSafe
class DiffBuilder extends BaseComponent {

	final IntParam winOffset = new IntParam("0:Смещение:0-10").bind(config, "winOffset");
	final SealCheckService srv;

	CommandEnv env;
	int wo;
	Mat dm1, dm2;

	public DiffBuilder(SealCheckService srv) {
		super(srv.config.getConfig("diffBuilder"));
		this.srv = srv;
	}

	Mat build(Rectangle mr) {
		env = srv.env;
		wo = winOffset.v;
		dm1 = runPreDiff(srv.tm1);
		Mat m2 = buildMat2(mr);
		dm2 = runPreDiff(m2);
		return runBuildDiff();
	}

	private Mat buildMat2(Rectangle mr) {
		Rectangle r = new Rectangle(mr);
		r.translate(-wo / 2, -wo / 2);
		return cropMat(srv.m2, r);
	}

	Mat runPreDiff(Mat m) {
		return srv.script.runTask("preDiff", m);
	}

	Mat runBuildDiff() {
		Rectangle wr = new Rectangle(srv.objRect);
		wr.setBounds(0, 0, wr.width - wo, wr.height - wo);
		env.putMat("dm1", cropMat(dm1, wr));
		List<Mat> dms = new ArrayList<>();
		range(wo + 1).forEach(x -> range(wo + 1).forEach(y -> {
			wr.setLocation(x, y);
			env.putMat("dm2", cropMat(dm2, wr));
			srv.script.runTask("buildDiff");
			dms.add(env.mat.clone());
		}));
		Mat rm = new Mat(wr.height, wr.width, dm1.type(), Scalar.WHITE);
		dms.forEach(m -> min(rm, m, rm));
		env.putMat("dr", rm);
		return srv.script.runTask("postDiff", rm);
	}

}