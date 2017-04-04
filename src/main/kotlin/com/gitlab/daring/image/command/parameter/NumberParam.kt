package com.gitlab.daring.image.command.parameter

abstract class NumberParam<T : Number>(sv: String, sp: String) : CommandParam<T>(sv, sp) {

    @JvmField val minValue: T
    @JvmField val maxValue: T

    init {
        val ss = spec.split('-')
        minValue = parseValue(ss[0])
        maxValue = parseValue(ss[1])
    }

    abstract fun setNumValue(v: Number)

    val pv get() = v.toDouble() * 0.01

}