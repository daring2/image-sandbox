package com.gitlab.daring.image.command.parameter

open class StringParam(sv: String) : CommandParam<String>(sv, "") {

    override fun parseValue(sv: String) = sv

}