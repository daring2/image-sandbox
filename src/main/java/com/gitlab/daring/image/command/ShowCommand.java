package com.gitlab.daring.image.command;

import com.gitlab.daring.image.component.BaseCanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import static com.gitlab.daring.image.swing.SwingUtils.runInEdt;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class ShowCommand extends BaseCommand {

	final String title = args[0];
	final BaseCanvasFrame frame = new BaseCanvasFrame(title);
	final ToMat converter = new ToMat();

	public ShowCommand(String... params) {
		super(params);
		frame.setDefaultCloseOperation(isCacheable() ? HIDE_ON_CLOSE : DISPOSE_ON_CLOSE);
	}

	@Override
	public boolean isCacheable() {
		return !title.isEmpty();
	}

	@Override
	public void execute(CommandEnv env) {
		runInEdt(() ->
			frame.showImage(converter.convert(env.mat))
		);
	}

	@Override
	public void close() {
		frame.dispose();
	}

}
