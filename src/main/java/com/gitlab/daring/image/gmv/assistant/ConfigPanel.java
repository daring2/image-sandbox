package com.gitlab.daring.image.gmv.assistant;

import com.gitlab.daring.image.command.CommandScriptPanel;
import com.gitlab.daring.image.swing.BaseFrame;

import javax.swing.*;
import java.awt.*;

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
		addStaticParams(pc.rectSize, db.sampleOpacity, db.templateOpacity, pc.minValue);
		applyEvent.onFire(this::save);
		setScript(tb.config.getString("script"));
	}

	void save() {
		tb.buildCmd = script.getCommand();
		saveConfig(ShotAssistant.ConfigPath);
	}

	void showFrame() {
		JFrame frame = new BaseFrame("Configuration", this);
		Rectangle b = assistant.getFrame().getBounds();
		frame.setBounds(b.x + b.width , b.y, 640, 400);
		frame.setVisible(true);
	}

}
