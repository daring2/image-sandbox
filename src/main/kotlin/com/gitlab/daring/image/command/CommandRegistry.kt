package com.gitlab.daring.image.command

import com.gitlab.daring.image.MainContext
import com.gitlab.daring.image.command.CommandUtils.parseArgs
import com.gitlab.daring.image.command.CommandUtils.splitScript
import com.gitlab.daring.image.command.combine.CombineCommands
import com.gitlab.daring.image.command.drawing.DrawCommands
import com.gitlab.daring.image.command.env.EnvCommands
import com.gitlab.daring.image.command.feature.FeaturesCommands
import com.gitlab.daring.image.command.structure.StructureCommands
import com.gitlab.daring.image.command.template.TemplateCommands
import com.gitlab.daring.image.command.transform.TransformCommands
import com.gitlab.daring.image.config.ConfigUtils.defaultConfig
import com.gitlab.daring.image.util.CacheUtils.buildClosableCache
import com.gitlab.daring.image.util.ExceptionUtils.throwArgumentException
import com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim
import java.util.*

typealias CommandFactory = (Array<String>) -> Command

class CommandRegistry : AutoCloseable {

    val config = defaultConfig.getConfig("isb.CommandRegistry")
    val factories = HashMap<String, CommandFactory>()
    val cache = buildClosableCache<String, Command>(config.getString("cache"))

    val commands get() = factories.keys

    init {
        EnvCommands.register(this)
        TransformCommands.register(this)
        CombineCommands.register(this)
        DrawCommands.register(this)
        StructureCommands.register(this)
        TemplateCommands.register(this)
        FeaturesCommands.register(this)
        MainContext.closeEvent.onFire { this.close() }
    }

    fun register(name: String, f: CommandFactory) {
        factories.put(name, f)
    }

    fun parseScript(script: String): ScriptCommand {
        val cmds = splitScript(script).map(this::getCommand)
        return ScriptCommand(script, cmds)
    }

    private fun getCommand(exp: String): Command {
        cache.getIfPresent(exp)?.let { return it }
        return parseCommand(exp).apply { if (cacheable) cache.put(exp, this) }
    }

    private fun parseCommand(cmdStr: String): Command {
        val ss = splitAndTrim(cmdStr, "()")
        val args = parseArgs(ss.getOrNull(1) ?: "")
        val cf = factories[ss[0]] ?: throwArgumentException("Invalid command " + cmdStr)
        return cf(args)
    }

    override fun close() {
        cache.invalidateAll()
        cache.cleanUp()
    }

}