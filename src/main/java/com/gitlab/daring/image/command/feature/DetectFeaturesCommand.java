package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;
import org.bytedeco.javacpp.opencv_features2d.Feature2D;
import org.bytedeco.javacpp.opencv_features2d.GFTTDetector;

import static com.gitlab.daring.image.util.EnumUtils.findEnum;

public class DetectFeaturesCommand extends BaseCommand {

	final Feature2D detector = createDetector();

	public DetectFeaturesCommand(String... args) {
		super(args);
	}

	@Override
	public void execute(CommandEnv env) {
		detector.detect(env.mat, env.keyPoints);
	}

	Feature2D createDetector() {
		Detector d = findEnum(Detector.class, args[0]);
		if (d == Detector.GFTT) {
			return GFTTDetector.create();
		} else {
			throw new IllegalArgumentException("detector=" + d);
		}
	}

	enum Detector { GFTT }

}
