package com.gitlab.daring.image.config

import com.google.common.io.Files
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigFactory.parseFile
import com.typesafe.config.ConfigParseOptions
import com.typesafe.config.ConfigRenderOptions.concise
import java.io.File
import java.nio.charset.StandardCharsets.UTF_8

object ConfigUtils {

    val AppConfigFile = "config/application.conf"

    val defaultConfig = loadDefaultConfig()

    private fun loadDefaultConfig(): Config {
        val f = File(AppConfigFile)
        val c = if (f.exists()) parseFile(f) else emptyConfig()
        return c.withFallback(ConfigFactory.load())
    }

    @JvmStatic
    fun defaultConfig(path: String) = defaultConfig.getConfig(path)

    @JvmStatic
    fun referenceConfig() = ConfigFactory.defaultReference()

    @JvmStatic
    fun emptyConfig() = ConfigFactory.empty()

    @JvmStatic
    fun loadConfig(path: String?): Config {
        val c = ConfigFactory.load(ConfigParseOptions.defaults())
        return if (path != null) c.getConfig(path) else c
    }

    @JvmStatic
    fun configFromMap(map: Map<String, Any>): Config {
        return ConfigFactory.parseMap(map)
    }

    @JvmStatic
    fun configFromString(str: String) = ConfigFactory.parseString(str)

    @JvmStatic
    fun saveConfig(c: Config, file: String) {
        val str = c.root().render(concise().setFormatted(true).setJson(false))
        Files.write(str, File(file), UTF_8)
    }

    @JvmStatic
    fun saveDiffConfig(c: Config, file: String) {
        val db = ConfigDiffBuilder()
        val dc = loadConfig(null)
        val sc = db.build(c.withFallback(dc), referenceConfig())
        saveConfig(sc, file)
    }

    @JvmStatic
    fun getIntOpt(c: Config, path: String, defValue: Int): Int {
        return if (c.hasPath(path)) c.getInt(path) else defValue
    }

}