package com.gitlab.daring.image.command

import com.gitlab.daring.image.command.parameter.EnumParam

abstract class KBaseCommand(args: Array<String>): BaseCommand(*args) {

    inline fun <reified T: Enum<T>> enumParam(dv: T?): EnumParam<T>  {
        return addParam(EnumParam<T>(T::class.java, nextArg(dv)))
    }

}