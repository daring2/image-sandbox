package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.*;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.ImageUtils.newScalarMat;
import static org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.BLACK;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class WatershedCenterCommand extends BaseCommand {

	final IntParam r1 = intParam(0, "0-100");
	final IntParam r2 = intParam(1, "0-100");
	final EnumParam<MarkerType> markerType = enumParam(MarkerType.class, 2);
	final IntParam segment = intParam(3, "0-2");
	final IntParam thickness = intParam(4, "0-10");
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

	void drawMarker(Mat m, IntParam p, int color) {
		Rect cr = getCenterRect(m.size(), p.v * 0.01);
		Scalar c = Scalar.all(color);
		int th = thickness.v;
		MarkerType mt = markerType.v;
		if (mt == MarkerType.Rectangle) {
			rectangle(m, cr, c, th, LINE_8, 0);
		} else if (mt == MarkerType.Circle) {
			Point cp = new Point(m.cols() / 2, m.rows() / 2);
			circle(m, cp, cr.width() / 2, c, th, LINE_8, 0);
		} else {
			throw new IllegalArgumentException("markerType=" + mt);
		}
	}

	enum MarkerType { Rectangle, Circle }

}
