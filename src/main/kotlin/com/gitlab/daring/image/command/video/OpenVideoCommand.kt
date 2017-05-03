package com.gitlab.daring.image.command.video

import com.gitlab.daring.image.MainContext.timer
import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.video.VideoUtils.getVideoFps
import com.gitlab.daring.image.video.VideoUtils.newVideoCapture
import org.bytedeco.javacpp.opencv_videoio.VideoCapture
import kotlin.concurrent.schedule

internal class OpenVideoCommand(args: Array<String>) : KBaseCommand(args) {

    val inputParam = fileParam("0")
    val fpsParam = intParam(20, "0-100")

    var input = ""
    var fps = 0
    var capture: VideoCapture? = null

    override fun execute(env: CommandEnv) {
        val i = inputParam.v
        if (i != input) {
            capture?.close()
            input = i
            val vc = newVideoCapture(i).also { capture = it }
            fps = getVideoFps(vc, fpsParam.v)
        }
        capture?.let { vc ->
            if (vc.read(env.mat))
                env.finishEvent.addListener(this::onFinish)
        }
    }

    fun onFinish(env: CommandEnv?) {
        if (env == null) return
        env.finishEvent.removeListener(this::onFinish)
        if (capture?.isOpened ?: false) {
            timer.schedule(1000L / fps) { env.startEvent.fire(env) }
        }
    }

}