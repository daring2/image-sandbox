package com.gitlab.daring.image.component;

import org.bytedeco.javacv.CanvasFrame;

import java.awt.*;

import static com.gitlab.daring.image.util.GeometryUtils.scaleToMax;

public class BaseCanvasFrame extends CanvasFrame {

	final Dimension maxSize = new Dimension(1024, 768);

	public BaseCanvasFrame(String title) {
		super(title, 1);
		setVisible(false);
	}

	@Override
	public void showImage(Image image) {
		if (!isVisible()) needInitialResize = true;
		super.showImage(image);
		if (!isVisible()) {
			resize();
			setVisible(true);
		}
	}

	public void resize() {
		Dimension d = scaleToMax(getCanvasSize(), maxSize);
		setCanvasSize(d.width, d.height);
	}
	
}
