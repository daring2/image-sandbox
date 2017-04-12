package com.gitlab.daring.image.command.parameter

import java.lang.Boolean.parseBoolean

class BooleanParam(sv: String) : CommandParam<Boolean>(sv, "") {

    override fun parseValue(sv: String): Boolean {
        return parseBoolean(sv)
    }

}