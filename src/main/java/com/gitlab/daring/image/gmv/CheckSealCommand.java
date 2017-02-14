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
import static com.gitlab.daring.image.util.ImageUtils.buildMat;
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

	public CheckSealCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		Mat m1 = imread(f1.v, 0);
		Mat m2 = imread(f2.v, 0);

		Rect cr1 = getCenterRect(m1.size(), scale.v * 0.01);
		Mat cm = m1.apply(cr1);
		MatchResult mr = tm.findBest(m2, cm);

		//TODO use getAffineTransform

		Mat dm1 = buildDiff(m1, m2);
		Rect cr2 = new Rect(toOpencv(mr.point), cr1.size());
		Mat dm2 = buildDiff(cm, m2.apply(cr2));

		rectangle(m1, cr1, Scalar.WHITE, 3, LINE_8, 0);
		showMat(m1, "Sample");
		rectangle(m2, cr2, Scalar.WHITE, 3, LINE_8, 0);
		showMat(m2, "Image");
		showMat(dm1, "Difference");
		showMat(dm2, "Match");
	}

	Mat buildDiff(Mat m1, Mat m2) {
		return buildMat(r -> absdiff(m1, m2, r));
	}

}
