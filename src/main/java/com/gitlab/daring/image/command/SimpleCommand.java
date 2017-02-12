package com.gitlab.daring.image.command;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.stream.IntStream.range;

public class SimpleCommand extends BaseCommand {

	public Consumer<CommandEnv> func;

	public SimpleCommand(String... args) {
		super(args);
	}

	public SimpleCommand withFunc(Consumer<Mat> c) {
		func = env -> c.accept(env.mat);
		return this;
	}

	public SimpleCommand withFunc(int n, Consumer<Mat> c) {
		func = env -> range(0, n).forEach(i -> c.accept(env.mat));
		return this;
	}

	public SimpleCommand withFunc(BiConsumer<Mat, Mat> f) {
		Mat d = new Mat();
		func = env -> { f.accept(env.mat, d); env.mat = d; };
		return this;
	}

	public SimpleCommand withSetFunc(Function<Mat, Mat> f) {
		func = env -> env.mat = f.apply(env.mat);
		return this;
	}

	public SimpleCommand withCombFunc(String mk, BiConsumer<Mat, Mat> f) {
		func = env -> f.accept(env.mat, env.getMat(mk));
		return this;
	}

	@Override
	public void execute(CommandEnv env) {
		func.accept(env);
	}

}
