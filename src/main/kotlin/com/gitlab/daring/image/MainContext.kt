package com.gitlab.daring.image

import com.gitlab.daring.image.command.CommandRegistry
import com.gitlab.daring.image.event.UnitEvent
import java.util.*

object MainContext : AutoCloseable {

    @JvmField
    val closeEvent = UnitEvent()
    @JvmField
    val timer = Timer(true)
    @JvmField
    val commandRegistry = CommandRegistry()

    override fun close() {
        timer.cancel()
        closeEvent.fire()
    }

}