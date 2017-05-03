package com.gitlab.daring.image.event

class UnitEvent: ValueEvent<Unit>() {

    fun addListener(l: () -> Unit) {
        addListener(UnitListener(l))
    }

    fun removeListener(l: () -> Unit) {
        removeListener(UnitListener(l))
    }

    fun onFire(l: () -> Unit) = addListener(l)

    fun onFire(l: Runnable) = onFire(l::run)

    fun fire() = fire(null)

}

data class UnitListener(val l: () -> Unit): Listener<Unit> {
    override fun invoke(p1: Unit?) = l.invoke()
}