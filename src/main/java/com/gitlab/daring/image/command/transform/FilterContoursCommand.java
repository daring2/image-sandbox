package com.gitlab.daring.image.command.transform;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Scalar;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.gitlab.daring.image.util.EnumUtils.findEnum;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;
import static java.lang.Double.isNaN;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class FilterContoursCommand extends BaseCommand {

	final Mode mode = findEnum(Mode.values(), params[0]);
	final Metric metric = findEnum(Metric.values(), params[1]);
	final double minValue = doubleParam(2);
	final double maxValue = doubleParam(3);

	public FilterContoursCommand(String... params) {
		super(params);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m = env.mat;
		if (minValue == 0 && isNaN(maxValue)) return;
		MatVector cs = new MatVector();
		findContours(m, cs, mode.ordinal(), CHAIN_APPROX_NONE);
		List<Mat> rcs = new ArrayList<>();
		for (long i = 0, size = cs.size(); i < size; i++) {
			Mat c = cs.get(i);
			double mv = calcMetric(c);
			if (mv >= minValue && (mv < maxValue || isNaN(maxValue)))
			rcs.add(c);
		}
		env.mat = new Mat(m.size(), m.type(), Scalar.BLACK);
		drawContours(env.mat, toOpencv(rcs), -1, Scalar.WHITE);
	}

	double calcMetric(Mat c) {
		if (metric == Metric.Length) {
			return arcLength(c, false);
		} else if (metric == Metric.Area) {
			return contourArea(c);
		} else if (metric == Metric.Size){
			Rectangle r = toJava(boundingRect(c));
			return Math.max(r.width, r.height);
		} else if (metric == Metric.Diameter) {
			Rectangle r = toJava(boundingRect(c));
			return sqrt(pow(r.width, 2)  + pow(r.height, 2));
		} else {
			throw new IllegalArgumentException("metric=" + metric);
		}
	}

	enum Mode { External, List, CComp, Tree, FloodFill }

	enum Metric { Length, Area, Size, Diameter}
	
}
