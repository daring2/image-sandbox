package com.gitlab.daring.image.command.parameter

class IntParam (sv: String, sp: String = "") : NumberParam<Int>(sv, sp) {

    val fv get() = v.toFloat()
    val dv get() = v.toDouble()

    override fun parseValue(sv: String) = sv.toInt()

    override fun setNumValue(v: Number) {
        value = v.toInt()
    }

    fun posVal(dv: Int) = if (v > 0) v else dv

}