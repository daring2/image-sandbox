package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.command.feature.FeatureDetectorType.SURF

internal class FeatureDetectorCommand(args: Array<String>) : KBaseCommand(args) {

    val detectorType = enumParam(SURF)

    override fun execute(env: CommandEnv) {
        val dt = detectorType.v
        env.featureDetector = dt.create()
    }

}