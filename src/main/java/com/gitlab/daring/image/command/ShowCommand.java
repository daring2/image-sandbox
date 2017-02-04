package com.gitlab.daring.image.command;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import static com.gitlab.daring.image.swing.SwingUtils.runInEdt;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class ShowCommand extends BaseCommand {

	final String title = args[0];
	final CanvasFrame frame = new CanvasFrame(title, 1);
	final ToMat converter = new ToMat();

	public ShowCommand(String... params) {
		super(params);
		frame.setDefaultCloseOperation(isCacheable() ? DO_NOTHING_ON_CLOSE : DISPOSE_ON_CLOSE);
	}

	@Override
	public boolean isCacheable() {
		return !title.isEmpty();
	}

	@Override
	public void execute(CommandEnv env) {
		Frame f = converter.convert(env.mat);
		runInEdt(() -> frame.showImage(f));
	}

	@Override
	public void close() {
		frame.dispose();
	}

}
