package com.gitlab.daring.image

import com.gitlab.daring.image.event.VoidEvent

object MainContext : AutoCloseable {

    @JvmField
    val closeEvent = VoidEvent()

    override fun close() {
        closeEvent.fire()
    }

}