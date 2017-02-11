package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.EnumParam;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Scalar;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;
import static java.lang.Double.isNaN;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class FilterContoursCommand extends BaseCommand {

	final EnumParam<Mode> mode = enumParam(Mode.class, 0);
	final EnumParam<Metric> metric = enumParam(Metric.class, 1);
	final DoubleParam minValue = doubleParam(2, "0-1000");
	final DoubleParam maxValue = doubleParam(3, "0-1000");
	final EnumParam<ApproxMethod> method = enumParam(ApproxMethod.class, 4);

	public FilterContoursCommand(String... params) {
		super(params);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m = env.mat;
		if (minValue.v == 0 && isNaN(maxValue.v)) return;
		MatVector cs = new MatVector();
		findContours(m, cs, mode.vi(), method.vi() + 1);
		List<Mat> rcs = new ArrayList<>();
		for (long i = 0, size = cs.size(); i < size; i++) {
			Mat c = cs.get(i);
			double mv = calcMetric(c);
			if (mv >= minValue.v && (mv < maxValue.v || isNaN(maxValue.v)))
			rcs.add(c);
		}
		env.mat = new Mat(m.size(), m.type(), Scalar.BLACK);
		drawContours(env.mat, toOpencv(rcs), -1, Scalar.WHITE);
	}

	double calcMetric(Mat c) {
		Metric m = metric.v;
		if (m == Metric.Length) {
			return arcLength(c, false);
		} else if (m == Metric.Area) {
			return contourArea(c);
		} else if (m == Metric.Size){
			Rectangle r = toJava(boundingRect(c));
			return Math.max(r.width, r.height);
		} else if (m == Metric.Diameter) {
			Rectangle r = toJava(boundingRect(c));
			return sqrt(pow(r.width, 2)  + pow(r.height, 2));
		} else {
			throw new IllegalArgumentException("metric=" + m);
		}
	}

	enum Mode { External, List, CComp, Tree, FloodFill }

	enum Metric { Length, Area, Size, Diameter}

	enum ApproxMethod { None, Simple, TC89_L1, TC89_KCOS }
	
}
