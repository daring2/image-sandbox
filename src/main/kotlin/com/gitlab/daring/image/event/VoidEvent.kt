package com.gitlab.daring.image.event

import java.util.function.Consumer

class VoidEvent: ValueEvent<Void>() {

    fun onFire(l: Runnable) {
        addListener(Consumer { _ -> l.run() })
    }

    fun fire() {
        fire(null)
    }

}