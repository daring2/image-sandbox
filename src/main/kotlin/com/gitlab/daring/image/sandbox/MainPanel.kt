package com.gitlab.daring.image.sandbox

import com.gitlab.daring.image.command.CommandScriptPanel
import com.gitlab.daring.image.command.parameter.FileParam
import com.gitlab.daring.image.command.parameter.StringParam

internal class MainPanel(val sb: ImageSandbox) : CommandScriptPanel() {

    val filesParam = createFilesParam()

    init {
        setScript(sb.config.getString("script"))
    }

    fun createFilesParam(): StringParam {
        val p = FileParam(":Файлы:0").bind<FileParam>(sb.config, "files")
        addStaticParams(p)
        return p
    }

}