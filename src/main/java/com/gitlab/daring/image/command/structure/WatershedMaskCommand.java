package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.command.parameter.StringParam;
import org.bytedeco.javacpp.opencv_core.*;

import static com.gitlab.daring.image.util.ImageUtils.smat;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class WatershedMaskCommand extends BaseCommand {

	final StringParam maskKey = stringParam("");
	final IntParam segment = intParam(0, "0-10");

	final Mat lm = new Mat();
	final Mat rm = new Mat();

	public WatershedMaskCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m = env.getMat(maskKey.v);
		threshold(m, m, 128, 255, THRESH_BINARY);
		connectedComponents(m, lm);
		watershed(env.mat, lm);
		max(lm, 0).asMat().convertTo(rm, CV_8U);
		Mat si = smat(segment.v);
		inRange(rm, si, si, rm);
		env.mat = rm;
	}

}
