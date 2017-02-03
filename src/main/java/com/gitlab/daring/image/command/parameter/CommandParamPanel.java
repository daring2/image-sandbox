package com.gitlab.daring.image.command.parameter;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.List;

public class CommandParamPanel extends JPanel {

	public CommandParamPanel() {
		setLayout(new MigLayout("fill, wrap 2", "[right][grow,fill]", "[center]"));
	}

	public void setParams(List<CommandParam<?>> params) {
		removeAll();
		params.forEach(this::addParam);
		revalidate();
	}

	void addParam(CommandParam<?> p) {
		if (p.name.isEmpty()) return;
		if (p instanceof NumberParam<?>) {
			addNumberParam((NumberParam<?>) p);
		} else if (p instanceof EnumParam<?>) {
			addEnumParam((EnumParam<?>) p);
		}
	}

	void addNumberParam(NumberParam<?> p) {
		JSlider sl = new JSlider(p.minValue.intValue(), p.maxValue.intValue(), p.v.intValue());
		sl.setMajorTickSpacing((sl.getMaximum() - sl.getMinimum()) / 10);
		sl.setPaintLabels(true);
		sl.addChangeListener(e -> p.setNumValue(sl.getValue())); //TODO check getValueIsAdjusting
		addComponent(p.name, sl);
	}

	<T extends Enum<T>> void addEnumParam(EnumParam<T> p) {
		T[] vs = p.enumClass.getEnumConstants();
		JComboBox<T> cb = new JComboBox<T>(vs);
		cb.setSelectedItem(p.v);
		cb.addActionListener(e -> p.setValue(vs[cb.getSelectedIndex()]));
		addComponent(p.name, cb);
	}

	void addComponent(String label, JComponent comp) {
		add(new JLabel(label));
		add(comp);
	}

}
