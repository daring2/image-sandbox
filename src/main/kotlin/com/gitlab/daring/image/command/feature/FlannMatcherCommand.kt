package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_features2d.FlannBasedMatcher

internal class FlannMatcherCommand (vararg args: String) : KBaseCommand(*args) {

    //TODO support all params

    override fun execute(env: CommandEnv) {
        env.descriptorMatcher = FlannBasedMatcher()
    }

}