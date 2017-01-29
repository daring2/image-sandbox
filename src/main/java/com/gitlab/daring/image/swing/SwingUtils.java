package com.gitlab.daring.image.swing;

import javax.swing.*;

public class SwingUtils {

	public static JButton newButton(String label, Runnable act) {
		JButton b = new JButton(label);
		b.addActionListener(e -> act.run());
		return b;
	}

	public static JSlider newPercentSlider() {
		JSlider sl = new JSlider(0, 100);
		sl.setMajorTickSpacing(10);
		sl.setPaintLabels(true);
		return sl;
	}

	private SwingUtils() {
	}

}