package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_core.Point;

import java.awt.*;

import static com.gitlab.daring.image.util.GeometryUtils.getCenter;
import static com.gitlab.daring.image.util.ImageUtils.newScalarMat;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;
import static org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.BLACK;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.circle;
import static org.bytedeco.javacpp.opencv_imgproc.watershed;

public class WatershedCenterCommand extends BaseCommand {

	final IntParam r1 = intParam(0, "0-100");
	final IntParam r2 = intParam(1, "0-100");
	final Mat rm = new Mat();

	public WatershedCenterCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Dimension d = toJava(env.mat.size());
		Mat mks = new Mat(d.height, d.width, CV_32SC1, BLACK);
		Point cp = toOpencv(getCenter(d));
		circle(mks, cp, d.width * r1.v / 100, Scalar.all(1));
		circle(mks, cp, d.width * r2.v / 100, Scalar.WHITE);
//		Size size = env.mat.size();
//		rectangle(mks, getCenterRect(size, 0.01 * r1.v), Scalar.all(1));
//		rectangle(mks, getCenterRect(size, 0.01 * r2.v), Scalar.WHITE);
		watershed(env.mat, mks);
		mks.convertTo(rm, CV_8U, 255 , 255);
		compare(rm, newScalarMat(0), rm, CMP_EQ);
		env.mat = rm;
	}

}
