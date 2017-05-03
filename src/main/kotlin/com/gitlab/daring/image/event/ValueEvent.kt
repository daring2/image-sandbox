package com.gitlab.daring.image.event

import java.util.concurrent.CopyOnWriteArrayList

open class ValueEvent<T> {

    var enabled = true

    private val listeners = CopyOnWriteArrayList<Listener<T>>()

    fun addListener(l: Listener<T>) {
        if (!listeners.contains(l))
            listeners.add(l)
    }

    fun removeListener(l: Listener<T>) {
        listeners.remove(l)
    }

    fun removeListeners() {
        listeners.clear()
    }

    fun fire(value: T?) {
        if (!enabled) return
        listeners.forEach { it.invoke(value) }
    }

}

typealias Listener<T> = (T?) -> Unit