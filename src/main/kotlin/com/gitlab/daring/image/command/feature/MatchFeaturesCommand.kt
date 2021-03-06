package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.features.DMatchResult
import com.gitlab.daring.image.features.FeatureUtils.detectAndCompute

internal class MatchFeaturesCommand(args: Array<String>) : KBaseCommand(args) {

    val matKey = stringParam("")

    override fun execute(env: CommandEnv) {
        val m1 = env.mat
        val m2 = env.getMat(matKey.v)

        val fd = env.featureDetector
        val ps1 = detectAndCompute(fd, m1)
        val ps2 = detectAndCompute(fd, m2)

        val dm = env.descriptorMatcher
        val mr = DMatchResult(ps1, ps2)
        dm.match(ps1.descriptors, ps2.descriptors, mr.matches)
        env.matchResult = mr
    }

}