package com.gitlab.daring.image.command;

import com.gitlab.daring.image.component.BaseCanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;

import java.awt.*;

import static com.gitlab.daring.image.swing.SwingUtils.runInEdt;
import static com.gitlab.daring.image.util.GeometryUtils.scaleToMax;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class ShowCommand extends BaseCommand {

	final String title = args[0];
	final BaseCanvasFrame frame = new BaseCanvasFrame(title);
	final ToMat converter = new ToMat();
	final Dimension maxSize = new Dimension(1024, 768);

	public ShowCommand(String... params) {
		super(params);
		frame.setDefaultCloseOperation(isCacheable() ? HIDE_ON_CLOSE : DISPOSE_ON_CLOSE);
		frame.setVisible(false);
	}

	@Override
	public boolean isCacheable() {
		return !title.isEmpty();
	}

	@Override
	public void execute(CommandEnv env) {
		runInEdt(() -> {
			frame.showImage(converter.convert(env.mat));
			if (!frame.isVisible()) {
				resize();
				frame.setVisible(true);
			}
		});
	}

	void resize() {
		Dimension d = scaleToMax(frame.getCanvasSize(), maxSize);
		frame.setCanvasSize(d.width, d.height);
	}

	@Override
	public void close() {
		frame.dispose();
	}

}
