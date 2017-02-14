package com.gitlab.daring.image.gmv;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.command.parameter.StringParam;
import com.gitlab.daring.image.template.MatchResult;
import com.gitlab.daring.image.template.TemplateMatcher;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;

import javax.annotation.concurrent.NotThreadSafe;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.ImageUtils.showMat;
import static com.gitlab.daring.image.util.OpencvConverters.toOpencv;
import static org.bytedeco.javacpp.opencv_core.LINE_8;
import static org.bytedeco.javacpp.opencv_core.absdiff;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

@NotThreadSafe
public class CheckSealCommand extends BaseCommand {

	final StringParam f1 = stringParam("");
	final StringParam f2 = stringParam("");
	final IntParam scale = intParam(30, "0-100");

	final TemplateMatcher tm = new TemplateMatcher();
	Mat m1, m2;

	public CheckSealCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		m1 = imread(f1.v, 0);
		m2 = imread(f2.v, 0);
		Rect cr = getCenterRect(m1.size(), scale.v * 0.01);
		Mat cm = m1.apply(cr);
		MatchResult mr = tm.findBest(m2, cm);
		Mat rm = new Mat();
		absdiff(m1, m2, rm);

		rectangle(m1, cr, Scalar.WHITE, 3, LINE_8, 0);
		showMat(m1, "Sample");
		Rect mrect = new Rect(toOpencv(mr.point), cm.size());
		rectangle(m2, mrect, Scalar.BLUE, 3, LINE_8, 0);
		showMat(m1, "Image");
		showMat(rm, "Difference");
	}

}
