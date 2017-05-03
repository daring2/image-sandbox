package com.gitlab.daring.image.command.combine

import com.gitlab.daring.image.command.CommandRegistry

object CombineCommands {

    fun register(r: CommandRegistry) {
        r.register("combine", ::CombineCommand);
        r.register("addWeighted", ::AddWeightedCommand);
        r.register("relativeDiff", ::RelativeDiffCommand);
        r.register("bitwisePrev", ::BitwisePrevCommand);
        r.register("bitwisePrev", ::BitwisePrevCommand);
    }

}