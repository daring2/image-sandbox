package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.Command
import com.gitlab.daring.image.command.CommandRegistry
import com.gitlab.daring.image.command.CommandUtils.parseIntParams
import com.gitlab.daring.image.command.SimpleCommand
import com.gitlab.daring.image.util.ImageUtils.cropCenter
import org.bytedeco.javacpp.opencv_core.Rect

object GeometricCommands {

    @JvmStatic
    fun register(r: CommandRegistry) {
        r.register("cropRect", this::cropRectCommand)
        r.register("cropCenter", this::cropCenterCommand)
        r.register("scale", ::ScaleCommand)
        r.register("scaleToSize", ::ScaleToSizeCommand)
        r.register("cropRectVar", ::CropRectVarCommand)
    }

    fun cropRectCommand(args: Array<String>): Command {
        val c = SimpleCommand(*args)
        val ps = parseIntParams(*args)
        val rect = Rect(ps[0], ps[1], ps[2], ps[3])
        return c.withSetFunc { m -> m.apply(rect) }
    }

    fun cropCenterCommand(args: Array<String>): Command {
        val c = SimpleCommand(*args)
        val sp = c.intParam(100, "0-100")
        return c.withSetFunc { m -> cropCenter(m, sp.pv) }
    }

}
