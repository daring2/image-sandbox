package com.gitlab.daring.image.command.parameter

import com.gitlab.daring.image.util.EnumUtils.findEnum
import kotlin.reflect.KClass

class EnumParam<T : Enum<T>>(val enumClass: KClass<T>, sv: String) : CommandParam<T>(sv, "") {

    init {
        v = parseValue(args[0])
    }

    val vi get() = v.ordinal

    val javaEnumClass = enumClass.java //TODO remove

    //TODO refactor
    override fun parseValue(sv: String): T? {
        return if (enumClass != null) findEnum(enumClass.java, sv) else null
    }

    override fun configValue(): Any {
        return "$v"
    }

}
