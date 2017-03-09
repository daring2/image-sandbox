package com.gitlab.daring.image.command.drawing;

import com.gitlab.daring.image.command.CommandRegistry;

public class DrawCommands {

    public static void register(CommandRegistry r) {
        DrawCommands f = new DrawCommands();
        r.register("drawCenter", DrawCenterCommand::new);
    }

}
