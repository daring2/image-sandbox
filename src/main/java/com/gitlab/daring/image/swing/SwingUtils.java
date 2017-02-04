package com.gitlab.daring.image.swing;

import javax.swing.*;

import static com.gitlab.daring.image.util.CommonUtils.tryRun;
import static javax.swing.SwingUtilities.invokeAndWait;
import static javax.swing.SwingUtilities.isEventDispatchThread;

public class SwingUtils {

	public static boolean isEdt() {
		return isEventDispatchThread();
	}

	public static void runInEdt(Runnable r) {
		tryRun(() -> {
			if (isEdt()) r.run(); else invokeAndWait(r);
		});
	}

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