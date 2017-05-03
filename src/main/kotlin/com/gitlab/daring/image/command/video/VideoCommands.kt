package com.gitlab.daring.image.command.video

import com.gitlab.daring.image.command.CommandRegistry

object VideoCommands {

    fun register(r: CommandRegistry) {
        r.register("openVideo", ::OpenVideoCommand)
    }

}