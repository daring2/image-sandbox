package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.Command
import com.gitlab.daring.image.command.CommandRegistry
import com.gitlab.daring.image.command.KCommandUtils.newCommand
import org.bytedeco.javacpp.opencv_core.Scalar
import org.bytedeco.javacpp.opencv_features2d.DrawMatchesFlags.DRAW_RICH_KEYPOINTS
import org.bytedeco.javacpp.opencv_features2d.drawKeypoints

object FeaturesCommands {

    @JvmStatic
    fun register(r: CommandRegistry) {
        r.register("drawKeyPoints", this::drawKeyPointsCommand)
        r.register("detectFeatures", ::DetectFeaturesCommand)
        r.register("newBFMatcher", ::BFMatcherCommand)
        r.register("newFlannMatcher", ::FlannMatcherCommand)
        r.register("matchFeatures", ::MatchFeaturesCommand)
        r.register("showFeatureMatches", ::ShowFeatureMatchesCommand)
        r.register("findHomography", ::FindHomographyCommand)
        r.register("warpPerspective", ::WarpPerspectiveCommand)
        FeatureDetectorType.register(r)
    }

    fun drawKeyPointsCommand(ps: Array<String>): Command {
        val color = Scalar.all(-1.0)
        return newCommand {
            env -> drawKeypoints(env.mat, env.keyPoints, env.mat, color, DRAW_RICH_KEYPOINTS)
        }
    }

}