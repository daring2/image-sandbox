package com.gitlab.daring.image.command;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SimpleCommand extends BaseCommand {

	public Consumer<CommandEnv> func;

	public SimpleCommand(String... args) {
		super(args);
	}

	public SimpleCommand withFunc(Consumer<Mat> c) {
		func = env -> c.accept(env.mat);
		return this;
	}

	public SimpleCommand withFunc(BiConsumer<Mat, Mat> f) {
		Mat d = new Mat();
		func = env -> { f.accept(env.mat, d); env.mat = d; };
		return this;
	}

	@Override
	public void execute(CommandEnv env) {
		func.accept(env);
	}

}