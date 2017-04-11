package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.CommandScriptUtils.runCommand
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.util.OpencvConverters.toJava
import org.bytedeco.javacpp.opencv_core
import org.bytedeco.javacpp.opencv_core.DMatchVector
import org.bytedeco.javacpp.opencv_features2d.DrawMatchesFlags.NOT_DRAW_SINGLE_POINTS
import org.bytedeco.javacpp.opencv_features2d.drawMatches
import java.nio.ByteBuffer

internal class ShowFeatureMatchesCommand(args: Array<String>) : KBaseCommand(args) {

    val maxFeatures = intParam(100, "0-100")
    val title = stringParam("matches")

    val color = opencv_core.Scalar.all(-1.0)
    val mask = ByteBuffer.allocate(0)
    val rm = opencv_core.Mat()

    override fun execute(env: CommandEnv) {
        val (ps1, ps2, ms) = env.matchResult
        val matches = toJava(ms).take(maxFeatures.v).toTypedArray()
        drawMatches(
                ps1.mat, ps1.points,
                ps2.mat, ps2.points,
                DMatchVector(*matches), rm,
                color, color, mask, NOT_DRAW_SINGLE_POINTS
        )
        env.mat = rm
        runCommand(env, "show", title.v)
    }

}