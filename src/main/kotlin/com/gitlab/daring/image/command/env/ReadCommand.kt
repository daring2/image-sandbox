package com.gitlab.daring.image.command.env

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_imgcodecs.imread
import java.io.File

internal class ReadCommand(vararg args: String) : KBaseCommand(*args) {

    val file = fileParam("")
    val flags = enumParam(ReadFlag.None)
    val key = stringParam("")
    val cache = boolParam(true)

    @Volatile var fileMat = Mat()
    @Volatile var fileTime = 0L

    override fun execute(env: CommandEnv) {
        val fn = env.eval(file.v)
        val ft = File(fn).lastModified()
        if (ft != fileTime || !cache.v) {
            fileMat = imread(fn, flags.vi() + 1)
            fileTime = ft
        }
        if (key.v.isEmpty()) {
            env.mat = fileMat.clone()
        } else {
            env.putMat(key.v, fileMat)
        }
    }

    enum class ReadFlag {
        None, Gray, Color
    }

}