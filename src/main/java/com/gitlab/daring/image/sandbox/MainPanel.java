package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.event.VoidEvent;
import com.gitlab.daring.image.swing.BaseAction;
import com.gitlab.daring.image.swing.BaseFrame;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

class MainPanel extends JPanel {

	final ImageSandbox sb;
	final VoidEvent applyEvent = new VoidEvent();

	MainPanel(ImageSandbox sb) {
		this.sb = sb;
		setLayout(new MigLayout("fill, wrap 2", "[right][grow,fill]", "[center]"));
		createScriptField();
		createButtons();
	}

	void createScriptField() {
		String script = sb.config.getString("script");
		JTextArea field = new JTextArea(script, 5, 10);
		add(new JLabel("Скрипт"), "left, span 2");
		add(new JScrollPane(field), "h 1000, grow, span 2");
		applyEvent.onFire(() -> sb.setScript(field.getText()));
	}

	void createButtons() {
		BaseAction act = new BaseAction("Применить", e -> applyEvent.fire() );
		act.register(this, "control S");
		add(new JButton(act), "left, span 2");
	}

	void showFrame() {
		BaseFrame frame = new BaseFrame("ImageSandbox", this);
		frame.setSize(640, 480);
		frame.setVisible(true);
	}

}
