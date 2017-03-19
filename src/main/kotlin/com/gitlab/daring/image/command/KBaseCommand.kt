package com.gitlab.daring.image.command

import com.gitlab.daring.image.command.parameter.EnumParam

abstract class KBaseCommand(vararg args: String): BaseCommand(*args) {

    inline fun <reified T> enumParam(dv: T): EnumParam<T> where T: Enum<T> {
        return addParam(EnumParam<T>(T::class.java, nextArg(dv)))
    }

}