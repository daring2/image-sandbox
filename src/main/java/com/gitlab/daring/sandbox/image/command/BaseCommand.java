package com.gitlab.daring.sandbox.image.command;

public abstract class BaseCommand implements Command {

	protected final String[] args;

	public BaseCommand(String[] args) {
		this.args = args;
	}

}
