package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.DoubleParam;
import com.gitlab.daring.image.command.parameter.EnumParam;

import java.util.Comparator;

import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;
import static java.util.stream.Collectors.toList;

public class FilterContoursCommand extends BaseCommand {

	final EnumParam<ContourMetric> metric = enumParam(ContourMetric.class, 0, ContourMetric.Length);
	final DoubleParam minValue = doubleParam(1, NaN, "0-1000");
	final DoubleParam maxValue = doubleParam(2, NaN, "0-1000");

	public FilterContoursCommand(String... params) {
		super(params);
	}

	@Override
	public void execute(CommandEnv env) {
		if (isNaN(minValue.v) && isNaN(maxValue.v)) return;
		ContourMetric m = metric.v;
		Comparator<Contour> mc = m.comparator.reversed();
		env.contours = env.contours.stream().filter(c -> {
			double mv = c.getMetric(m);
			return mv >= minValue.v && (mv < maxValue.v || isNaN(maxValue.v));
		}).sorted(mc).collect(toList());
	}

}
