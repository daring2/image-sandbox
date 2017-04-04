package com.gitlab.daring.image.command.drawing

import com.gitlab.daring.image.command.CommandRegistry

object DrawCommands {

    @JvmStatic
    fun register(r: CommandRegistry) {
        r.register("drawCenter", ::DrawCenterCommand)
    }

}