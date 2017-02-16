package com.gitlab.daring.image.assistant;

import com.gitlab.daring.image.command.CommandScriptPanel;
import com.gitlab.daring.image.swing.BaseFrame;

import javax.swing.*;
import java.awt.*;

import static com.gitlab.daring.image.sandbox.SandboxUtils.saveCompConfig;

class ConfigPanel extends CommandScriptPanel {

	final ShotAssistant assistant;
	final TemplateBuilder tb;
	final PositionControl pc;
	final DisplayBuilder db;

	ConfigPanel(ShotAssistant a) {
		this.assistant = a;
		this.tb = a.templateBuilder;
		this.pc = a.positionControl;
		this.db = a.displayBuilder;
		addStaticParams();
		applyEvent.onFire(this::save);
		setScript(tb.config.getString("script"));
	}

	void addStaticParams() {
		addStaticParam(db.sampleOpacity);
		addStaticParam(db.templateOpacity);
		addStaticParam(pc.minValue);
	}

	void save() {
		tb.buildCmd = script.getCommand();
		
		saveCompConfig(ShotAssistant.ConfigPath, m -> {
			m.put("position.limits.minValue", pc.minValue.v);
			m.put("display.sampleOpacity", db.sampleOpacity.v);
			m.put("display.templateOpacity", db.templateOpacity.v);
			m.put("template.script", script.getText());
		});
	}

	void showFrame() {
		JFrame frame = new BaseFrame("Configuration", this);
		Rectangle b = assistant.getFrame().getBounds();
		frame.setBounds(b.x + b.width , b.y, 640, 400);
		frame.setVisible(true);
	}

}
