package com.gitlab.daring.image.swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static javax.swing.KeyStroke.getKeyStroke;

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

    public void register(JComponent c, String keyStr) {
        c.getActionMap().put(getName(), this);
        if (!keyStr.isEmpty()) {
            KeyStroke ks = getKeyStroke(keyStr);
            c.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(ks, getName());
        }
    }

}
