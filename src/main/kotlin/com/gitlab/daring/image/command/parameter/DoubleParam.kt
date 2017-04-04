package com.gitlab.daring.image.command.parameter

class DoubleParam(sv: String, sp: String) : NumberParam<Double>(sv, sp) {

    override fun parseValue(sv: String) = sv.toDouble()

    override fun setNumValue(v: Number) {
        value = v.toDouble()
    }

}