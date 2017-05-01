package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand

internal class DetectFeaturesCommand(args: Array<String>) : KBaseCommand(args) {

    override fun execute(env: CommandEnv) {
        env.featureDetector.detect(env.mat, env.keyPoints)
    }

}