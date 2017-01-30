package com.gitlab.daring.image.assistant;

import com.gitlab.daring.image.config.ConfigDiffBuilder;
import com.gitlab.daring.image.swing.BaseAction;
import com.gitlab.daring.image.util.ValueEvent;
import com.typesafe.config.Config;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import static com.gitlab.daring.image.config.ConfigUtils.*;
import static com.gitlab.daring.image.swing.SwingUtils.newPercentSlider;
import static javax.swing.KeyStroke.getKeyStroke;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static org.slf4j.LoggerFactory.getLogger;

class ConfigPanel extends JPanel {

	final Logger log = getLogger(getClass());
	final ShotAssistant assistant;
	final TemplateBuilder tb;
	final DisplayBuilder db;
	final ValueEvent<String> applyEvent = new ValueEvent<>();

	ConfigPanel(ShotAssistant a) {
		this.assistant = a;
		this.tb = a.templateBuilder;
		this.db = a.displayBuilder;
		setLayout(new MigLayout("fill, wrap 2", "[right][grow,fill]", "[center]"));
		createSampleOpacitySlider();
		createTemplateOpacitySlider();
		add(new JSeparator(), "sx 2, width 100%");
		createScriptField();
		createApplyButton();
		applyEvent.addListener(v -> save());
	}

	void createSampleOpacitySlider() {
		JSlider sl = newPercentSlider();
		sl.addChangeListener(e -> db.sampleOpacity = sl.getValue() / 100.0);
		sl.setValue(getPercent(db.config, "sampleOpacity"));
		addComponent("Образец", sl);
	}

	void createTemplateOpacitySlider() {
		JSlider sl = newPercentSlider();
		sl.addChangeListener(e -> db.templateOpacity = sl.getValue() / 100.0);
		sl.setValue(getPercent(db.config, "templateOpacity"));
		addComponent("Контур", sl);
	}

	void createScriptField() {
		String script = tb.config.getString("script");
		JTextArea field = new JTextArea(script, 5, 10);
		add(new JLabel("Скрипт"), "left, span 2");
		add(new JScrollPane(field), "h 1000, grow, span 2");
		applyEvent.addListener(v -> tb.setScript(field.getText()));
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

	void save() {
		try {
			Config c = buildCurrentConfig(); //TODO refactor
			Config dc = new ConfigDiffBuilder().build(c, defaultConfig());
			saveConfig(dc, "conf/application.conf");
		} catch (IOException e) {
			log.warn("Cannot save configuration", e);
		}
	}

	Config buildCurrentConfig() {
		return configFromString(
			"display.sampleOpacity = " + db.sampleOpacity
			+ "\ndisplay.templateOpacity = " + db.templateOpacity
			+ "\ntemplate.script = \"" + tb.script + "\""
		).atPath("gmv.ShotAssistant");
	}

	void showFrame() {
		JFrame frame = new JFrame("Configuration");
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setContentPane(this);
		Rectangle b = assistant.getFrame().getBounds();
		frame.setBounds(b.x + b.width, b.y, 640, 300);
		frame.setVisible(true);
	}

}
