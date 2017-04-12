package com.gitlab.daring.image.command.structure

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.opencv.setTo
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_imgproc.createLineSegmentDetector

internal class DetectLinesCommand(args: Array<String>) : KBaseCommand(args) {

    //TODO support all params
    val detector = createLineSegmentDetector()

    val lines = Mat()

    override fun execute(env: CommandEnv) {
        detector.detect(env.mat, lines)
        env.mat.setTo(0)
        detector.drawSegments(env.mat, lines)
    }

}