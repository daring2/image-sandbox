package com.gitlab.daring.sandbox.image.assistant;

import com.typesafe.config.Config;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

class ConfigPanel extends JPanel {

	final ShotAssistant a;
	final Config config;

	ConfigPanel(ShotAssistant a) {
		this.a = a;
		this.config = a.config;
		setLayout(new MigLayout("fillx, wrap 2", "[right][grow,fill]", "[center]"));
		createSampleOpacitySlider();
	}

	void createSampleOpacitySlider() {
		JSlider sl = new JSlider(0, 100);
		sl.setMajorTickSpacing(10);
		sl.setPaintLabels(true);
		sl.addChangeListener(e -> a.sampleOpacity = sl.getValue() / 100.0);
		sl.setValue(config.getInt("sampleOpacity"));
		addComponent("Sample opacity", sl);
	}

	void addComponent(String label, JComponent comp) {
		add(new JLabel(label));
		add(comp);
	}

	void showFrame() {
		JFrame frame = new JFrame("Configuration");
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setContentPane(this);
		frame.setSize(640, 480);
		frame.setVisible(true);
	}

}
