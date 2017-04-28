package com.gitlab.daring.image.event

class VoidEvent: ValueEvent<Void>() {

    fun onFire(l: () -> Unit) {
        addListener { _ -> l() }
    }

    fun fire() = fire(null)

}