package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.*;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.GC_INIT_WITH_RECT;
import static org.bytedeco.javacpp.opencv_imgproc.grabCut;

public class GrubCutCenterCommand extends BaseCommand {

	final IntParam size = intParam(0, 90, "0-100");
	final IntParam iterCount = intParam(1, 5, "0-100");

	final Mat bm = new Mat();
	final Mat fm = new Mat();

	public GrubCutCenterCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m = new Mat(env.mat.size(), CV_8U);
		Rect cr = getCenterRect(m.size(), size.v * 0.01);
		grabCut(env.mat, m, cr, bm, fm, iterCount.v, GC_INIT_WITH_RECT);
		env.mat = multiply(and(m, Scalar.all(1)), 255).asMat();
	}

}
