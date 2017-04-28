package com.gitlab.daring.image.command

import com.gitlab.daring.image.MainContext.mainContext
import com.gitlab.daring.image.command.CommandUtils.parseArgs
import com.gitlab.daring.image.command.CommandUtils.splitScript
import com.gitlab.daring.image.command.combine.CombineCommands
import com.gitlab.daring.image.command.drawing.DrawCommands
import com.gitlab.daring.image.command.env.EnvCommands
import com.gitlab.daring.image.command.feature.FeaturesCommands
import com.gitlab.daring.image.command.structure.StructureCommands
import com.gitlab.daring.image.command.template.TemplateCommands
import com.gitlab.daring.image.command.transform.TransformCommands
import com.gitlab.daring.image.config.ConfigUtils
import com.gitlab.daring.image.util.CacheUtils.buildClosableCache
import com.gitlab.daring.image.util.ExceptionUtils.throwArgumentException
import com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim
import java.util.*

typealias CommandFactory = (Array<String>) -> Command

class CommandRegistry : AutoCloseable {

    val config = ConfigUtils.defaultConfig("isb.CommandRegistry")
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
        mainContext().closeEvent.onFire { this.close() }
    }

    fun register(name: String, f: CommandFactory) {
        factories.put(name, f)
    }

    fun parseScript(script: String): ScriptCommand {
        val cmds = splitScript(script).map { this.getCommand(it) }.toList()
        return ScriptCommand(script, cmds)
    }

    private fun getCommand(cmdStr: String): Command {
        return cache.getIfPresent(cmdStr) ?: parseCommand(cmdStr).apply {
            if (isCacheable) cache.put(cmdStr, this)
        }
    }

    private fun parseCommand(cmdStr: String): Command {
        val ss = splitAndTrim(cmdStr, "()")
        val args = parseArgs(ss.getOrElse(1, {""}))
        val cf = factories[ss[0]] ?: throwArgumentException("Invalid command " + cmdStr)
        return cf(args)
    }

    override fun close() {
        cache.invalidateAll()
        cache.cleanUp()
    }

    companion object {

        val Instance = CommandRegistry() //TODO move to main context

        @JvmStatic
        fun commandRegistry() = Instance

    }

}