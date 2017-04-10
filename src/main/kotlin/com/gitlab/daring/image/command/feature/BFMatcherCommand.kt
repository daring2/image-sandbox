package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.command.feature.BFMatcherCommand.NormType.HAMMING
import com.gitlab.daring.image.command.feature.BFMatcherCommand.NormType.HAMMING2
import org.bytedeco.javacpp.opencv_features2d.BFMatcher
import org.bytedeco.javacpp.opencv_features2d.ORB
import org.bytedeco.javacpp.opencv_xfeatures2d.SIFT
import org.bytedeco.javacpp.opencv_xfeatures2d.SURF

internal class BFMatcherCommand(vararg args: String) : KBaseCommand(*args) {

    val normType = enumParam(NormType.AUTO)
    val crossCheck = boolParam(true)

    override fun execute(env: CommandEnv) {
        var nt = normType.v
        if (nt == NormType.AUTO) nt = selectNormType(env)
        env.descriptorMatcher = BFMatcher(nt.code, crossCheck.v)
    }

    internal fun selectNormType(env: CommandEnv): NormType {
        val fd = env.featureDetector
        return when (fd) {
            is SURF, is SIFT -> NormType.L2
            is ORB -> if (fd.wtA_K == 3 || fd.wtA_K == 4) HAMMING2 else HAMMING
            else -> HAMMING
        }
    }

    enum class NormType (val code: Int) {
        AUTO(0), L1(2), L2(4), HAMMING(6), HAMMING2(7)
    }

}
