package com.gitlab.daring.image.command.parameter;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.List;

public class BaseParamPanel extends JPanel {

	public BaseParamPanel() {
		setLayout(new MigLayout("fill, wrap 2", "[right][grow,fill]", "[center]"));
	}

	public void setParams(List<CommandParam<?>> params) {
		removeAll();
		params.forEach(this::addParam);
	}

	void addParam(CommandParam<?> p) {
		if (p.name.isEmpty()) return;
		if (p instanceof NumberParam<?>) {
			addNumberParam((NumberParam<?>) p);
		} else if (p instanceof EnumParam<?>) {
			//TODO implement
		}
	}

	void addNumberParam(NumberParam<?> p) {
		JSlider sl = new JSlider(p.minValue.intValue(), p.maxValue.intValue(), p.v.intValue());
		sl.setMajorTickSpacing((sl.getMaximum() - sl.getMinimum()) / 10);
		sl.setPaintLabels(true);
		sl.addChangeListener(e -> p.setValue(sl.getValue()));
		addComponent(p.name, sl);
	}

	void addComponent(String label, JComponent comp) {
		add(new JLabel(label));
		add(comp);
	}

}
