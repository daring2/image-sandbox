package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.*;

import static com.gitlab.daring.image.command.structure.MarkerType.Circle;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class GrubCutCenterCommand extends BaseCommand {

	final IntParam r1 = intParam(0, 5,"0-100");
	final IntParam r2 = intParam(1, 90,"0-100");
	final EnumParam<MarkerType> mt = enumParam(MarkerType.class, 2, Circle);
	final IntParam iterCount = intParam(3, 5, "0-100");

	final Rect rect = new Rect();
	final Mat bm = new Mat();
	final Mat fm = new Mat();

	public GrubCutCenterCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m = new Mat(env.mat.size(), CV_8U, Scalar.BLACK);
		mt.v.drawCenter(m, r2, GC_PR_FGD, CV_FILLED);
		mt.v.drawCenter(m, r1, GC_FGD, CV_FILLED);
//		env.mat = multiply(m, 128).asMat();
		grabCut(env.mat, m, rect, bm, fm, iterCount.v, GC_INIT_WITH_MASK);
		env.mat = multiply(and(m, Scalar.all(1)), 255).asMat();
	}

}
