package com.gitlab.daring.sandbox.image.assistant;

import com.gitlab.daring.sandbox.image.swing.BaseAction;
import com.gitlab.daring.sandbox.image.util.ValueEvent;
import com.typesafe.config.Config;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static com.gitlab.daring.sandbox.image.swing.SwingUtils.newPercentSlider;
import static javax.swing.KeyStroke.getKeyStroke;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

class ConfigPanel extends JPanel {

	final ShotAssistant a;
	final Config config;
	final ValueEvent<String> applyEvent = new ValueEvent<>();

	ConfigPanel(ShotAssistant a) {
		this.a = a;
		this.config = a.config;
		setLayout(new MigLayout("fill, wrap 2", "[right][grow,fill]", "[center]"));
		createSampleOpacitySlider();
		createTemplateOpacitySlider();
		add(new JSeparator(), "sx 2, width 100%");
		createScriptField();
		createApplyButton();
	}

	void createSampleOpacitySlider() {
		JSlider sl = newPercentSlider();
		sl.addChangeListener(e -> a.sampleOpacity = sl.getValue() / 100.0);
		sl.setValue(config.getInt("sampleOpacity"));
		addComponent("Образец", sl);
	}

	void createTemplateOpacitySlider() {
		JSlider sl = newPercentSlider();
		sl.addChangeListener(e -> a.templateOpacity = sl.getValue() / 100.0);
		sl.setValue(config.getInt("templateOpacity"));
		addComponent("Контур", sl);
	}

	void createScriptField() {
		String script = config.getString("template.script");
		JTextArea field = new JTextArea(script, 5, 10);
		add(new JLabel("Скрипт"), "left, span 2");
		add(new JScrollPane(field), "h 1000, grow, span 2");
		applyEvent.addListener(v -> a.templateBuilder.setScript(field.getText()));
	}

	void createApplyButton() {
		BaseAction act = new BaseAction("Применить", e -> applyEvent.fire("") );
		add(new JButton(act), "left, span 2");
		getActionMap().put(act.getName(), act);
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(getKeyStroke("control S"), act.getName());
	}

	void addComponent(String label, JComponent comp) {
		add(new JLabel(label));
		add(comp);
	}

	void showFrame() {
		JFrame frame = new JFrame("Configuration");
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setContentPane(this);
		Rectangle b = a.getFrame().getBounds();
		frame.setBounds(b.x + b.width, b.y, 640, 300);
		frame.setVisible(true);
	}

}
