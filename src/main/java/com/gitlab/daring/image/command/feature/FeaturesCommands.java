package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.Command;
import com.gitlab.daring.image.command.CommandRegistry;
import com.gitlab.daring.image.command.SimpleCommand;
import org.bytedeco.javacpp.opencv_core.Scalar;

import static org.bytedeco.javacpp.opencv_features2d.DrawMatchesFlags.DRAW_RICH_KEYPOINTS;
import static org.bytedeco.javacpp.opencv_features2d.drawKeypoints;

public class FeaturesCommands {

	public static void register(CommandRegistry r) {
		FeaturesCommands f = new FeaturesCommands();
		r.register("drawKeyPoints", f::drawKeyPointsCommand);
		r.register("detectFeaturesGFTT", GFTTCommand::new);
		r.register("detectFeaturesSURF", SURFCommand::new);
		r.register("detectFeaturesSIFT", SIFTCommand::new);
		r.register("detectFeaturesORB", ORBCommand::new);
		r.register("detectFeaturesSTAR", StarDetectorCommand::new);
		r.register("detectFeaturesMSER", MSERCommand::new);
		r.register("detectFeaturesSB", SimpleBlobCommand::new);
	}

	public Command drawKeyPointsCommand(String... ps) {
		SimpleCommand c = new SimpleCommand(ps);
		Scalar color = Scalar.all(-1);
		c.func = env -> drawKeypoints(env.mat, env.keyPoints, env.mat, color, DRAW_RICH_KEYPOINTS);
		return c;
	}

}
