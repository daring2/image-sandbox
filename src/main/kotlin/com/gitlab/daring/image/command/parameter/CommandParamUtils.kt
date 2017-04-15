package com.gitlab.daring.image.command.parameter

object CommandParamUtils {

    @JvmStatic
    fun buildParamConfig(params: Collection<CommandParam<*>>): Map<String, Any> {
        return params.filter { it.configPath != null }
                .associateBy( {it.configPath }, CommandParam<*>::configValue)
    }

}