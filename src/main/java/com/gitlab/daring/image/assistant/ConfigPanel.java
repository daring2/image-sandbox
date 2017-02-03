package com.gitlab.daring.image.assistant;

import com.gitlab.daring.image.command.parameter.ParamPanel;
import com.gitlab.daring.image.event.VoidEvent;
import com.gitlab.daring.image.swing.BaseAction;
import com.gitlab.daring.image.swing.BaseFrame;
import com.typesafe.config.Config;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static com.gitlab.daring.image.config.ConfigUtils.configFromMap;
import static com.gitlab.daring.image.config.ConfigUtils.saveDiffConfig;
import static com.gitlab.daring.image.swing.SwingUtils.newPercentSlider;

class ConfigPanel extends JPanel {

	final ShotAssistant assistant;
	final TemplateBuilder tb;
	final DisplayBuilder db;
	final ParamPanel paramPanel = new ParamPanel();
	final VoidEvent applyEvent = new VoidEvent();

	ConfigPanel(ShotAssistant a) {
		this.assistant = a;
		this.tb = a.templateBuilder;
		this.db = a.displayBuilder;
		setLayout(new MigLayout("fill, wrap 2", "[right][grow,fill]", "[center]"));
		createSampleOpacitySlider();
		createTemplateOpacitySlider();
		add(paramPanel, "sx 2, width 100%");
		add(new JSeparator(), "sx 2, width 100%");
		createScriptField();
		createButtons();
		applyEvent.onFire(this::save);
		save();
	}

	void createSampleOpacitySlider() {
		JSlider sl = newPercentSlider();
		sl.addChangeListener(e -> db.sampleOpacity = sl.getValue());
		sl.setValue(db.config.getInt("sampleOpacity"));
		addComponent("Образец", sl);
	}

	void createTemplateOpacitySlider() {
		JSlider sl = newPercentSlider();
		sl.addChangeListener(e -> db.templateOpacity = sl.getValue());
		sl.setValue(db.config.getInt("templateOpacity"));
		addComponent("Контур", sl);
	}

	void createScriptField() {
		String script = tb.config.getString("script");
		JTextArea field = new JTextArea(script, 5, 10);
		add(new JLabel("Скрипт"), "left, span 2");
		add(new JScrollPane(field), "h 1000, grow, span 2");
		applyEvent.onFire(() -> tb.setScript(field.getText()));
	}

	void createButtons() {
		BaseAction act = new BaseAction("Применить", e -> applyEvent.fire() );
		act.register(this, "control S");
		add(new JButton(act), "left, span 2");
	}

	void addComponent(String label, JComponent comp) {
		add(new JLabel(label));
		add(comp);
	}

	void save() {
		paramPanel.setParams(tb.buildCmd.getParams());
		saveDiffConfig(buildConfig(), "conf/application.conf");
	}

	Config buildConfig() {
		Map<String, Object> m = new HashMap<>();
		m.put("display.sampleOpacity", db.sampleOpacity);
		m.put("display.templateOpacity", db.templateOpacity);
		m.put("template.script", tb.script);
		return configFromMap(m).atPath("gmv.ShotAssistant");
	}

	void showFrame() {
		JFrame frame = new BaseFrame("Configuration", this);
		Rectangle b = assistant.getFrame().getBounds();
		frame.setBounds(b.x + b.width , b.y, 640, 300);
		frame.setVisible(true);
	}

}
