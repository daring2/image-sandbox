package com.gitlab.daring.image.command;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ShowCommand extends BaseCommand {

	final String title = params[0];
	final CanvasFrame frame = new CanvasFrame(title, 1);
	final ToMat converter = new ToMat();

	public ShowCommand(String... params) {
		super(params);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public boolean isCacheable() {
		return !title.isEmpty();
	}

	@Override
	public void execute(CommandEnv env) {
		frame.showImage(converter.convert(env.mat));
	}

	@Override
	public void close() {
		frame.dispose();
	}

}
