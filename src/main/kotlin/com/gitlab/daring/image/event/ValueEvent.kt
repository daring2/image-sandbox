package com.gitlab.daring.image.event

import java.util.concurrent.CopyOnWriteArrayList
import java.util.function.Consumer

open class ValueEvent<T> : Consumer<T?> {

    var enabled = true

    private val listeners = CopyOnWriteArrayList<Consumer<T?>>()

    fun addListener(l: Consumer<T?>) {
        if (!listeners.contains(l))
            listeners.add(l)
    }

    fun removeListener(l: Consumer<T?>) {
        listeners.remove(l)
    }

    fun removeListeners() {
        listeners.clear()
    }

    fun fire(value: T?) {
        if (!enabled) return
        listeners.forEach { it.accept(value) }
    }

    override fun accept(v: T?) {
        fire(v)
    }

}