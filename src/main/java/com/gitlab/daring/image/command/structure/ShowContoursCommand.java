package com.gitlab.daring.image.command.structure;

import com.gitlab.daring.image.command.BaseCommand;
import com.gitlab.daring.image.command.CommandEnv;

public class ShowContoursCommand extends BaseCommand {

	final FindContoursCommand findCmd = new FindContoursCommand();
	final FilterContoursCommand filterCmd = new FilterContoursCommand(args);
	final DrawContoursCommand drawCmd = new DrawContoursCommand();

	public ShowContoursCommand(String... args) {
		super(args);
		params.addAll(filterCmd.getParams());
	}

	@Override
	public void execute(CommandEnv env) {
		findCmd.execute(env);
		filterCmd.execute(env);
		drawCmd.execute(env);
	}

}