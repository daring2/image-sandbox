package com.gitlab.daring.image.command;

import com.gitlab.daring.image.command.parameter.CommandParam;
import com.gitlab.daring.image.command.parameter.CommandParamPanel;
import com.gitlab.daring.image.event.VoidEvent;
import com.gitlab.daring.image.swing.BaseAction;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CommandScriptPanel extends JPanel {

	public final VoidEvent applyEvent = new VoidEvent();
	public final VoidEvent changeEvent = new VoidEvent();
	public final CommandScript script = new CommandScript();

	final JTextArea scriptField;
	final CommandParamPanel paramPanel = new CommandParamPanel();
	final List<CommandParam<?>> staticParams = new ArrayList<>();
	final Consumer<Void> changeListener = e -> changeEvent.fire();

	public CommandScriptPanel() {
		setLayout(new MigLayout("fill, wrap 1", "[fill]", "[center]"));
		scriptField = createScriptField();
		createButtons();
		add(new JSeparator(), "w 100%");
		add(paramPanel, "w 100%");
		applyEvent.onFire(this::apply);
	}

	JTextArea createScriptField() {
		JTextArea field = new JTextArea("", 5, 20);
		add(new JLabel("Скрипт"), "left");
		add(new JScrollPane(field), "h 1000");
		return field;
	}

	void createButtons() {
		BaseAction act = new BaseAction("Применить", e -> applyEvent.fire() );
		act.register(this, "control S");
		add(new JButton(act), "left, grow 0");
	}

	public <T> CommandParam<T> addStaticParam(CommandParam<T> p) {
		staticParams.add(p);
		return p;
	}

	public void setScript(String script) {
		scriptField.setText(script);
		applyEvent.fire();
	}

	void apply() {
		script.setText(scriptField.getText());
		script.addParamChangeListener(changeListener);
		paramPanel.setParams(buildParams());
	}

	List<CommandParam<?>> buildParams() {
		List<CommandParam<?>> ps = new ArrayList<>(staticParams);
		ps.addAll(script.command.getParams());
		return ps;
	}

}
