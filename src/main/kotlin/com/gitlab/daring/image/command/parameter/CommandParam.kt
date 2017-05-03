package com.gitlab.daring.image.command.parameter

import com.gitlab.daring.image.event.UnitEvent
import com.typesafe.config.Config

abstract class CommandParam<T: Any>(sv: String, sp: String) {

    val args = parseArgs(sv)
    val name = arg(1)
    val spec = arg(2, sp)
    var configPath = ""

    @Volatile @JvmField
    var vr: T? = null //TODO refactor

    var value: T
        get() {
            return vr ?: parseValue(arg(0)).apply { vr = this }
        }
        set(v) {
            if (vr != v) { vr = v; changeEvent.fire() }
        }

    var stringValue
        get() = value.toString()
        set(v) { value = parseValue(v) }

    val v get() = value

    @JvmField
    val changeEvent = UnitEvent()

    fun arg(i: Int, dv: String = "") = args.getOrNull(i) ?: dv

    open fun parseArgs(sv: String): List<String> {
        return " $sv".split(':').map(String::trim)
    }

    abstract fun parseValue(sv: String): T

    fun <P : CommandParam<T>> bind(c: Config, path: String): P {
        configPath = path
        stringValue = c.getString(path)
        return this as P
    }

    open fun configValue(): Any  = v

}