package com.gitlab.daring.image.command.parameter

import com.gitlab.daring.image.util.EnumUtils.findEnum
import kotlin.reflect.KClass

class EnumParam<T : Enum<T>>(val enumClass: KClass<T>, sv: String) : CommandParam<T>(sv, "") {

    val vi get() = v.ordinal

    val javaEnumClass = enumClass.java //TODO remove

    override fun parseValue(sv: String): T {
        return findEnum(enumClass.java, sv)
    }

    override fun configValue(): Any  = "$v"

}