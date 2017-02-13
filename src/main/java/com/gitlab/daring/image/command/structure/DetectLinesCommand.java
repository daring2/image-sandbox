package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_imgproc.LineSegmentDetector;

import static com.gitlab.daring.image.util.ImageUtils.newScalarMat;
import static org.bytedeco.javacpp.opencv_imgproc.createLineSegmentDetector;

public class DetectLinesCommand extends BaseCommand {

	//TODO support all params
	final LineSegmentDetector detector = createDetector();

	final Mat lines = new Mat();

	public DetectLinesCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		detector.detect(env.mat, lines);
		env.mat.setTo(newScalarMat(0));
		detector.drawSegments(env.mat, lines);
	}

	private LineSegmentDetector createDetector() {
		return createLineSegmentDetector();
	}

}