package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.EnumParam;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;

import java.awt.*;
import java.util.List;

import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;
import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toList;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class FilterContoursCommand extends BaseCommand {

	final EnumParam<Metric> metric = enumParam(Metric.class, 0, Metric.Length);
	final DoubleParam minValue = doubleParam(1, NaN, "0-1000");
	final DoubleParam maxValue = doubleParam(2, NaN, "0-1000");

	public FilterContoursCommand(String... params) {
		super(params);
	}

	@Override
	public void execute(CommandEnv env) {
		if (isNaN(minValue.v) && isNaN(maxValue.v)) return;
		List<Mat> cs = env.contours.stream().filter(c -> {
			double mv = calcMetric(c);
			return mv >= minValue.v && (mv < maxValue.v || isNaN(maxValue.v));
		}).collect(toList());
		Mat m = env.mat;
		env.mat = new Mat(m.size(), m.type(), Scalar.BLACK);
		//TODO add drawContours command
		drawContours(env.mat, toOpencv(cs), -1, Scalar.WHITE);
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

	enum Metric { Length, Area, Size, Diameter}

}
