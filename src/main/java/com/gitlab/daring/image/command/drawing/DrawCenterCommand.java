package com.gitlab.daring.image.command.drawing;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Scalar;

import java.awt.*;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;
import static org.bytedeco.javacpp.opencv_core.LINE_8;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class DrawCenterCommand extends BaseCommand {

	final EnumParam<Shape> shape = enumParam(Shape.class, 0);
	final IntParam scale = intParam(1, "0-100");
	final IntParam color = intParam(2, "0-255");
	final IntParam thickness = intParam(3, "0-10");

	public DrawCenterCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m = env.mat;
		Dimension d = toJava(m.size());
		Rectangle cr = getCenterRect(d, scale.v * 0.01);
		Scalar c = Scalar.all(color.v);
		int th = thickness.posVal(CV_FILLED);
		Shape sh = shape.v;
		if (sh == Shape.Rectangle) {
			rectangle(m, toOpencv(cr), c, th, LINE_8, 0);
		} else if (sh == Shape.Circle) {
			Point cp = new Point(d.width / 2, d.height / 2);
			circle(m, cp, cr.width / 2, c, th, LINE_8, 0);
		} else {
			throw new IllegalArgumentException("shape=" + sh);
		}
	}

	enum Shape { Rectangle, Circle }

}
