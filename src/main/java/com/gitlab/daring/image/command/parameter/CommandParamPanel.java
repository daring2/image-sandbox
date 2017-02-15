package com.gitlab.daring.image.command.parameter;

import com.gitlab.daring.image.event.VoidEvent;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Collections.emptyList;

public class CommandParamPanel extends JPanel {

	public final VoidEvent applyEvent = new VoidEvent();
	List<CommandParam<?>> params = emptyList();

	public CommandParamPanel() {
		setLayout(new MigLayout("fill, wrap 2", "[right][grow,fill]", "[center]"));
	}

	public void setParams(List<CommandParam<?>> params) {
		this.params = params;
		removeAll();
		params.forEach(this::addParam);
		revalidate();
	}

	public void addParamChangeListener(Consumer<Void> l) {
		params.forEach(p -> p.changeEvent.addListener(l));
	}

	void addParam(CommandParam<?> p) {
		if (p.name.isEmpty()) return;
		if (p instanceof NumberParam<?>) {
			addNumberParam((NumberParam<?>) p);
		} else if (p instanceof EnumParam<?>) {
			addEnumParam((EnumParam<?>) p);
		} else if (p instanceof BooleanParam) {
			addBooleanParam((BooleanParam) p);
		} else if (p instanceof StringParam) {
			addStringParam((StringParam) p);
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

	void addStringParam(StringParam p) {
		JTextField f = new JTextField(p.v);
		Runnable applyAction = () -> p.setValue(f.getText());
		f.addActionListener(e -> applyAction.run());
		applyEvent.onFire(applyAction);
		addComponent(p.name, f);
	}

	void addComponent(String label, JComponent comp) {
		add(new JLabel(label));
		add(comp);
	}

}
