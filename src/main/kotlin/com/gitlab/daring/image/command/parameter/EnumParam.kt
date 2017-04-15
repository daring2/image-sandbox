package com.gitlab.daring.image.command.parameter

import com.gitlab.daring.image.util.EnumUtils.findEnum
import kotlin.reflect.KClass

class EnumParam<T : Enum<T>>(val enumClass: KClass<T>, sv: String) : CommandParam<T>(sv, "") {

    val vi get() = v.ordinal

    override fun parseValue(sv: String) = findEnum(enumClass.java, sv)

    override fun configValue(): Any  = "$v"

}