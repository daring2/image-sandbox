package com.gitlab.daring.image.command.parameter

import com.gitlab.daring.image.util.EnumUtils.findEnum

class EnumParam<T : Enum<T>>(val enumClass: Class<T>, sv: String) : CommandParam<T>(sv, "") {

    val enumValues get() = enumClass.enumConstants

    val vi get() = v.ordinal

    override fun parseValue(sv: String) = findEnum(enumClass, sv)

    override fun configValue(): Any  = "$v"

}