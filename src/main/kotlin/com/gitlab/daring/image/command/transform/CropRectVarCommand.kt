package com.gitlab.daring.image.command.transform

import com.gitlab.daring.image.command.CommandEnv
import com.gitlab.daring.image.command.KBaseCommand
import com.gitlab.daring.image.util.ImageUtils.cropMat
import java.awt.Rectangle

internal class CropRectVarCommand(args: Array<String>) : KBaseCommand(args) {

    val rectKey = stringParam("rect");
    val matKey = stringParam("");

    override fun execute(env: CommandEnv) {
        val mk = matKey.v;
        val rect = env.getVar<Rectangle>(rectKey.v);
        env.putMat(mk, cropMat(env.getMat(mk), rect));
    }

}