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
	final Mat rm = new Mat();

	public WatershedCenterCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m = new Mat(env.mat.size(), CV_32SC1, BLACK);
		drawMarker(m, r1, 1);
		drawMarker(m, r2, 255);
		watershed(env.mat, m);
		m.convertTo(rm, CV_8U, 255 , 255);
		compare(rm, newScalarMat(0), rm, CMP_EQ);
		env.mat = rm;
	}

	void drawMarker(Mat m, IntParam p, int c) {
		Scalar color = Scalar.all(c);
		MarkerType mt = markerType.v;
		if (mt == MarkerType.Rectangle) {
			Rect rect = getCenterRect(m.size(), 0.01 * p.v);
			rectangle(m, rect, color);
		} else if (mt == MarkerType.Circle) {
			Point cp = new Point(m.cols() / 2, m.rows() / 2);
			circle(m, cp, m.cols() * p.v / 100, color);
		} else {
			throw new IllegalArgumentException("markerType=" + mt);
		}
	}

	enum MarkerType { Rectangle, Circle }

}
