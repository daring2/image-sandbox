package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.command.parameter.StringParam;
import org.bytedeco.javacpp.opencv_core.*;

import static com.gitlab.daring.image.util.ImageUtils.smat;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class GrubCutMarkerCommand extends BaseCommand {

	final StringParam markerKey = stringParam("");
	final IntParam iterCount = intParam(5, "0-100");

	final Mat cm = new Mat();
	final Rect rect = new Rect();
	final Mat bm = new Mat();
	final Mat fm = new Mat();

	public GrubCutMarkerCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m = env.getMat(markerKey.v);
		m.copyTo(cm);
		cm.setTo(smat(0));
		cm.setTo(smat(GC_PR_BGD), greaterThan(m, 100).asMat());
		cm.setTo(smat(GC_FGD), greaterThan(m, 200).asMat());
		grabCut(env.mat, cm, rect, bm, fm, iterCount.v, GC_INIT_WITH_MASK);
		env.mat = multiply(and(cm, Scalar.all(1)), 255).asMat();
	}

}