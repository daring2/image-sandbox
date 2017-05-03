package com.gitlab.daring.image

import com.gitlab.daring.image.command.CommandRegistry
import com.gitlab.daring.image.event.UnitEvent

object MainContext : AutoCloseable {

    @JvmField
    val closeEvent = UnitEvent()
    @JvmField
    val commandRegistry = CommandRegistry()

    override fun close() {
        closeEvent.fire()
    }

}