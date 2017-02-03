package com.gitlab.daring.image.assistant;

import com.gitlab.daring.image.command.parameter.CommandParam;
import com.gitlab.daring.image.command.parameter.ParamPanel;
import com.gitlab.daring.image.event.VoidEvent;
import com.gitlab.daring.image.swing.BaseAction;
import com.gitlab.daring.image.swing.BaseFrame;
import com.typesafe.config.Config;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gitlab.daring.image.config.ConfigUtils.configFromMap;
import static com.gitlab.daring.image.config.ConfigUtils.saveDiffConfig;

class ConfigPanel extends JPanel {

	final ShotAssistant assistant;
	final TemplateBuilder tb;
	final DisplayBuilder db;
	final ParamPanel paramPanel = new ParamPanel();
	final List<CommandParam<?>> staticParams = new ArrayList<>();
	final VoidEvent applyEvent = new VoidEvent();

	ConfigPanel(ShotAssistant a) {
		this.assistant = a;
		this.tb = a.templateBuilder;
		this.db = a.displayBuilder;
		addStaticParams();
		setLayout(new MigLayout("fill, wrap 2", "[right][grow,fill]", "[center]"));
		createScriptField();
		createButtons();
		add(new JSeparator(), "sx 2, width 100%");
		add(paramPanel, "sx 2, width 100%");
		applyEvent.onFire(this::save);
		applyEvent.fire();
	}

	void addStaticParams() {
		db.sampleOpacity.v = db.config.getInt("sampleOpacity");
		staticParams.add(db.sampleOpacity);
		db.templateOpacity.v = db.config.getInt("templateOpacity");
		staticParams.add(db.templateOpacity);
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

	void save() {
		paramPanel.setParams(buildParams());
		saveDiffConfig(buildConfig(), "conf/application.conf");
	}

	List<CommandParam<?>> buildParams() {
		List<CommandParam<?>> ps = new ArrayList<>(staticParams);
		ps.addAll(tb.buildCmd.getParams());
		return ps;
	}

	Config buildConfig() {
		Map<String, Object> m = new HashMap<>();
		m.put("display.sampleOpacity", db.sampleOpacity.v);
		m.put("display.templateOpacity", db.templateOpacity.v);
		m.put("template.script", tb.script);
		return configFromMap(m).atPath("gmv.ShotAssistant");
	}

	void showFrame() {
		JFrame frame = new BaseFrame("Configuration", this);
		Rectangle b = assistant.getFrame().getBounds();
		frame.setBounds(b.x + b.width , b.y, 640, 400);
		frame.setVisible(true);
	}

}
