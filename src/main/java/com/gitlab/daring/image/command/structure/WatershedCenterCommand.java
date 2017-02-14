package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.*;

import static com.gitlab.daring.image.command.structure.MarkerType.Circle;
import static com.gitlab.daring.image.util.ImageUtils.newScalarMat;
import static org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.BLACK;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.watershed;

public class WatershedCenterCommand extends BaseCommand {

	final IntParam r1 = intParam(5, "0-100");
	final IntParam r2 = intParam(30, "0-100");
	final EnumParam<MarkerType> mt = enumParam(MarkerType.class, Circle);
	final IntParam segment = intParam(0, "0-2");
	final Mat rm = new Mat();

	public WatershedCenterCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m = new Mat(env.mat.size(), CV_32SC1, BLACK);
		drawMarker(m, r1, 1);
		drawMarker(m, r2, 2);
		watershed(env.mat, m);
		max(m, 0).asMat().convertTo(rm, CV_8U);
		Mat si = newScalarMat(segment.v);
		inRange(rm, si, si, rm);
		env.mat = rm;
	}

	void drawMarker(Mat m, IntParam p, int c) {
		mt.v.drawCenter(m, p, c, c == 1 ? -1 : 1);
	}

}
