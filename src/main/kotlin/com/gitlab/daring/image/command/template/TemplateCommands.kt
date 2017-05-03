package com.gitlab.daring.image.command.template

import com.gitlab.daring.image.command.CommandRegistry

object TemplateCommands {

    fun register(r: CommandRegistry) {
        r.register("matchTemplate", ::MatchTemplateCommand)
    }

}