package com.gitlab.daring.image.swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class BaseAction extends AbstractAction {

	final Consumer<ActionEvent> act;

	public BaseAction(String name, Consumer<ActionEvent> act) {
		super(name);
		this.act = act;
	}

	public String getName() {
		return (String) getValue(NAME);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		act.accept(e);
	}

}
