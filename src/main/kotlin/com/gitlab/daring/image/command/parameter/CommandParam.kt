package com.gitlab.daring.image.command.parameter

import com.gitlab.daring.image.event.VoidEvent
import com.typesafe.config.Config

abstract class CommandParam<T: Any>(sv: String, sp: String) {

    val args = parseArgs(sv)
    val name = arg(1, "")
    val spec = arg(2, sp)
    var configPath = ""

    @Volatile @JvmField
    var vr: T? = null //TODO refactor

    var value: T
        get() {
            return vr ?: parseValue(arg(0, "")).apply { vr = this }
        }
        set(v) {
            if (vr == v) return
            vr = v
            changeEvent.fire()
        }

    val v get() = value

    @JvmField
    val changeEvent = VoidEvent()

    fun arg(i: Int, dv: String) = args.getOrNull(i) ?: dv

    open fun parseArgs(sv: String): List<String> {
        return " $sv".split(':').map(String::trim)
    }

    abstract fun parseValue(sv: String): T

    fun setStringValue(v: String) {
        value = parseValue(v)
    }

    fun <P : CommandParam<T>> bind(c: Config, path: String): P {
        configPath = path
        setStringValue(c.getString(path))
        return this as P
    }

    open fun configValue(): Any  = v

}