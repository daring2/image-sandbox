package com.gitlab.daring.image.command.drawing

import com.gitlab.daring.image.command.CommandRegistry

object DrawCommands {

    fun register(r: CommandRegistry) {
        r.register("drawCenter", ::DrawCenterCommand)
        r.register("drawVar", ::DrawVarCommand)
    }

}