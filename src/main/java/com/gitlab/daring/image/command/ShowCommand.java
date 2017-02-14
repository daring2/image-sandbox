package com.gitlab.daring.image.command;

import com.gitlab.daring.image.component.BaseCanvasFrame;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class ShowCommand extends BaseCommand {

	final String title = nextArg("");
	final BaseCanvasFrame frame = new BaseCanvasFrame(title);

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
		frame.showMat(env.mat);
	}

	@Override
	public void close() {
		frame.dispose();
	}

}
