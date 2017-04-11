package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_xfeatures2d.SURF

internal class DetectFeaturesCommand(args: Array<String>) : KBaseCommand(args) {

    override fun execute(env: CommandEnv) {
        if (env.featureDetector == null) env.featureDetector = SURF.create()
        env.featureDetector.detect(env.mat, env.keyPoints)
    }

}