package com.gitlab.daring.image.command;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ShowCommand extends BaseCommand {

	final CanvasFrame frame = new CanvasFrame(params[0], 1);
	final ToMat converter = new ToMat();

	public ShowCommand(String... params) {
		super(params);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void execute(CommandEnv env) {
		frame.showImage(converter.convert(env.mat));
	}

}
