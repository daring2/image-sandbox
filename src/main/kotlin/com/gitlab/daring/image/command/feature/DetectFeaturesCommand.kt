package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_xfeatures2d.SURF

internal class DetectFeaturesCommand(args: Array<String>) : KBaseCommand(args) {

    override fun execute(env: CommandEnv) {
        val fd = env.featureDetector ?: SURF.create().apply {
            env.featureDetector = this
        }
        fd.detect(env.mat, env.keyPoints)
    }

}