package com.gitlab.daring.image.component;

import org.bytedeco.javacv.CanvasFrame;

import java.awt.*;

public class BaseCanvasFrame extends CanvasFrame {

	public BaseCanvasFrame(String title) {
		super(title, 1);
	}

	@Override
	public void showImage(Image image) {
		if (!isVisible()) needInitialResize = true;
		super.showImage(image);
	}
	
}
