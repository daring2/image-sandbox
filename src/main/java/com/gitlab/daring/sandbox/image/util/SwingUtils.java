package com.gitlab.daring.sandbox.image.util;

import javax.swing.*;

public class SwingUtils {

	public static JButton newButton(String label, Runnable act) {
		JButton b = new JButton(label);
		b.addActionListener(e -> act.run());
		return b;
	}

	private SwingUtils() {
	}

}