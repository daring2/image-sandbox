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
		} else if (p instanceof BooleanParam) {
			addBooleanParam((BooleanParam) p);
		}
	}

	void addNumberParam(NumberParam<?> p) {
		JSlider sl = new JSlider(p.minValue.intValue(), p.maxValue.intValue(), p.v.intValue());
		int range = sl.getMaximum() - sl.getMinimum();
		sl.setMajorTickSpacing(range > 15 ? range / 10 : 1);
		sl.setPaintLabels(true);
		sl.addChangeListener(e -> p.setNumValue(sl.getValue()));
		addComponent(p.name, sl);
	}

	<T extends Enum<T>> void addEnumParam(EnumParam<T> p) {
		T[] vs = p.enumClass.getEnumConstants();
		JComboBox<T> cb = new JComboBox<T>(vs);
		cb.setSelectedItem(p.v);
		cb.addActionListener(e -> p.setValue(vs[cb.getSelectedIndex()]));
		addComponent(p.name, cb);
	}

	void addBooleanParam(BooleanParam p){
		JCheckBox b = new JCheckBox("", p.v);
		b.addItemListener(e -> p.setValue(b.isSelected()));
		addComponent(p.name, b);
	}

	void addComponent(String label, JComponent comp) {
		add(new JLabel(label));
		add(comp);
	}

}
