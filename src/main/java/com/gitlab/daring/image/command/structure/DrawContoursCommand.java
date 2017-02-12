package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Scalar;

import static com.gitlab.daring.image.util.CollectionUtils.mapList;
import static com.gitlab.daring.image.util.ImageUtils.newScalarMat;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;
import static org.bytedeco.javacpp.opencv_core.LINE_8;
import static org.bytedeco.javacpp.opencv_core.min;
import static org.bytedeco.javacpp.opencv_imgproc.CV_FILLED;
import static org.bytedeco.javacpp.opencv_imgproc.drawContours;

public class DrawContoursCommand extends BaseCommand {

	final IntParam index = intParam(0, 0, "0-10");
	final IntParam thickness = intParam(1, 1, "0-10");
	final int maxLevel = Integer.MAX_VALUE;

	final Mat h = new Mat();
	final Point offset = new Point();

	public DrawContoursCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		min(env.mat, newScalarMat(0), env.mat);
		if (index.v > env.contours.size()) return;
		MatVector cs = toOpencv(mapList(env.contours, c -> c.mat));
		int th = thickness.posVal(CV_FILLED);
		drawContours(env.mat, cs, index.v - 1, Scalar.WHITE, th, LINE_8, h, maxLevel, offset);
	}

}