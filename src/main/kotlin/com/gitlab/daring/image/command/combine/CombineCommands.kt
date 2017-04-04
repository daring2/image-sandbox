package com.gitlab.daring.image.command.combine

import com.gitlab.daring.image.command.Command
import com.gitlab.daring.image.command.CommandRegistry
import com.gitlab.daring.image.command.SimpleCommand

object CombineCommands {

    @JvmStatic
    fun register(r: CommandRegistry) {
        r.register("combine", this::combineCommand);
        r.register("addWeighted", ::AddWeightedCommand);
        r.register("relativeDiff", ::RelativeDiffCommand);
        r.register("bitwisePrev", ::BitwisePrevCommand);
        r.register("bitwisePrev", ::BitwisePrevCommand);
    }

    fun combineCommand(vararg ps: String): Command {
        val c = SimpleCommand(*ps)
        val mk = c.stringParam("")
        val op = c.enumParam<CombineMethod>(null)
        return c.withCombFunc(mk.v) { m1, m2 -> op.v.func(m1, m2, m1) }
    }

}