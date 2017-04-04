package com.gitlab.daring.image.command.parameter

class IntParam (sv: String, sp: String = "") : NumberParam<Int>(sv, sp) {

    fun posVal(dv: Int) = if (v > 0) v else dv

    override fun parseValue(sv: String) = sv.toInt()

    override fun setNumValue(v: Number) {
        value = v.toInt()
    }

}