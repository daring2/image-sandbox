package com.gitlab.daring.sandbox.image.commands;

public abstract class BaseCommand implements Command {

	protected final String[] args;

	public BaseCommand(String[] args) {
		this.args = args;
	}

}
