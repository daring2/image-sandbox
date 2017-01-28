package com.gitlab.daring.sandbox.image.command;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ShowCommand extends BaseCommand {

	final CanvasFrame frame = new CanvasFrame(args[0], 1);
	final ToMat converter = new ToMat();

	public ShowCommand(String... args) {
		super(args);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void execute(CommandEnv env) {
		frame.showImage(converter.convert(env.mat));
	}

}
