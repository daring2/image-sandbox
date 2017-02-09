package com.gitlab.daring.image.command.draw;

import com.gitlab.daring.image.command.CommandRegistry;

public class DrawCommands {

	public static void register(CommandRegistry r) {
		DrawCommands f = new DrawCommands();
		r.register("drawCenter", DrawCenterCommand::new);
	}

}
