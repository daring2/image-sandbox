package com.gitlab.daring.image.common

import com.gitlab.daring.image.config.ConfigUtils.defaultConfig
import com.typesafe.config.Config

abstract class BaseComponent(@JvmField val config: Config) {

    constructor(configPath: String) : this(defaultConfig(configPath))

    fun getConfig(path: String): Config {
        return config.getConfig(path)
    }

}