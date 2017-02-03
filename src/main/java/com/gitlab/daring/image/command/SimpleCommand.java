package com.gitlab.daring.image.command;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.function.Consumer;

public class SimpleCommand extends BaseCommand {

	Consumer<CommandEnv> func;

	public SimpleCommand(String... args) {
		super(args);
	}

	public SimpleCommand withFunc(Consumer<Mat> c) {
		func = env -> c.accept(env.mat);
		return this;
	}

	@Override
	public void execute(CommandEnv env) {
		func.accept(env);
	}

}
