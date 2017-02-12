package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import org.bytedeco.javacpp.opencv_core.*;

import static com.gitlab.daring.image.command.CommandUtils.parseIntParams;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.GC_INIT_WITH_RECT;
import static org.bytedeco.javacpp.opencv_imgproc.grabCut;

public class GrubCutCommand extends BaseCommand {

	final Mat bm = new Mat();
	final Mat fm = new Mat();

	public GrubCutCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m = new Mat(env.mat.size(), CV_8U);
		int[] ps = parseIntParams(args);
		Rect rect = new Rect(ps[0], ps[1], ps[2], ps[3]);
		grabCut(env.mat, m, rect, bm, fm, ps[4], GC_INIT_WITH_RECT);
		env.mat = multiply(and(m, Scalar.all(1)), 255).asMat();
	}

}
