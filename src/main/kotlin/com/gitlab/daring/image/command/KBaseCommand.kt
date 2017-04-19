package com.gitlab.daring.image.command

import com.gitlab.daring.image.command.parameter.*
import java.util.*

abstract class KBaseCommand(val args: Array<String>): Command {

    val params = ArrayList<CommandParam<*>>()

    fun <T : CommandParam<*>> addParam(p: T): T {
        params.add(p)
        return p
    }

    fun doubleParam(dv: Double, spec: String): DoubleParam {
        return addParam(DoubleParam(nextArg(dv), spec))
    }

    fun intParam(dv: Int, spec: String): IntParam {
        return addParam(IntParam(nextArg(dv), spec))
    }

    fun boolParam(dv: Boolean): BooleanParam {
        return addParam(BooleanParam(nextArg(dv)))
    }

    fun stringParam(dv: String): StringParam {
        return addParam(StringParam(nextArg(dv)))
    }

    fun fileParam(dv: String): FileParam {
        return addParam(FileParam(nextArg(dv)))
    }

    inline fun <reified T: Enum<T>> enumParam(dv: T?): EnumParam<T>  {
        return addParam(EnumParam<T>(T::class.java, nextArg(dv)))
    }

    fun arg(i: Int, dv: Any?) = args.getOrElse(i, { "" + dv })

    fun nextArg(dv: Any?) = arg(params.size, dv)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is KBaseCommand) return false
        if (!Arrays.equals(args, other.args)) return false
        return true
    }

    override fun hashCode(): Int  = Arrays.hashCode(args)

    override fun toString(): String {
        return "KBaseCommand{" +
                "params=" + Arrays.toString(args) +
                '}'
    }

}