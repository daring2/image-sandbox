package com.gitlab.daring.image.command.parameter

import java.io.File

class FileParam(sv: String) : StringParam(sv) {

    var maxCount = 1

    init {
        if (spec.isNotEmpty()) maxCount = spec.toInt()
    }

    override fun parseArgs(sv: String): Array<String> {
        val isFile = File(sv).exists()
        return if (isFile) arrayOf(sv) else super.parseArgs(sv)
    }

}