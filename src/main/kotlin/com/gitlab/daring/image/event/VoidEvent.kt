package com.gitlab.daring.image.event

class VoidEvent: ValueEvent<Void>() {

    fun onFire(l: () -> Unit) {
        addListener { _ -> l() }
    }

    fun onFire(l: Runnable) {
        onFire(l::run)
    }

    fun fire() = fire(null)

}