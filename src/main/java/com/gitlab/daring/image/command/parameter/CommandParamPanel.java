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

    public void setParams(List<CommandParam<?>> ps) {
        if (params.equals(ps)) return;
        params = ps;
        applyEvent.removeListeners();
        removeAll();
        ps.forEach(this::addParam);
        revalidate();
    }

    public void addParamChangeListener(Consumer<Void> l) {
        params.forEach(p -> p.changeEvent.addListener(l));
    }

    void addParam(CommandParam<?> p) {
        if (p.getName().isEmpty()) return;
        if (p instanceof ParamGroup) {
            addComponent(p.getName(), new JSeparator(), "");
        } else if (p instanceof NumberParam<?>) {
            addNumberParam((NumberParam<?>) p);
        } else if (p instanceof EnumParam<?>) {
            addEnumParam((EnumParam<?>) p);
        } else if (p instanceof BooleanParam) {
            addBooleanParam((BooleanParam) p);
        } else if (p instanceof FileParam) {
            addFileParam((FileParam) p);
        } else if (p instanceof StringParam) {
            addStringParam((StringParam) p);
        }
    }

    void addNumberParam(NumberParam<?> p) {
        JSlider sl = new JSlider(p.minValue.intValue(), p.maxValue.intValue(), p.getValue().intValue());
        int range = sl.getMaximum() - sl.getMinimum();
        sl.setMajorTickSpacing(range > 15 ? range / 10 : 1);
        sl.setPaintLabels(true);
        sl.addChangeListener(e -> p.setNumValue(sl.getValue()));
        JTextField f = newValueField(p, false);
        f.setColumns(5);
        addComponent(p.getName(), f, "split 2, growx 0");
        add(sl);
    }

    <T extends Enum<T>> void addEnumParam(EnumParam<T> p) {
        T[] vs = p.getJavaEnumClass().getEnumConstants();
        JComboBox<T> cb = new JComboBox<T>(vs);
        cb.setSelectedItem(p.getValue());
        cb.addActionListener(e -> p.setValue(vs[cb.getSelectedIndex()]));
        addComponent(p.getName(), cb, "");
    }

    void addBooleanParam(BooleanParam p) {
        JCheckBox b = new JCheckBox("", p.getValue());
        b.addItemListener(e -> p.setValue(b.isSelected()));
        addComponent(p.getName(), b, "");
    }

    void addFileParam(FileParam p) {
        JTextField f = newValueField(p, true);
        ParamFileChooser fc = new ParamFileChooser(p, f);
        addComponent(p.getName(), f, "split 2");
        add(fc.getOpenButton(), "growx 0");
    }

    void addStringParam(StringParam p) {
        JTextField f = newValueField(p, true);
        addComponent(p.getName(), f, "");
    }

    <T> JTextField newValueField(CommandParam<T> p, boolean editable) {
        JTextField f = new JTextField("" + p.getValue());
        if (editable) {
            f.addActionListener(e -> p.setStringValue(f.getText()));
            applyEvent.onFire(() -> p.vr = p.parseValue(f.getText()));
        } else {
            f.setEditable(false);
            p.changeEvent.onFire(() -> f.setText("" + p.getValue()));
        }
        return f;
    }

    void addComponent(String label, JComponent comp, String spec) {
        add(new JLabel(label));
        add(comp, spec);
    }

}
