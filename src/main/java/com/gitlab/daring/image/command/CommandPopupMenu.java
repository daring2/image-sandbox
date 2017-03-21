package com.gitlab.daring.image.command;

import com.gitlab.daring.image.swing.JScrollPopupMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;

class CommandPopupMenu extends JScrollPopupMenu {

    public CommandPopupMenu(JTextArea field) {
        super("Available commands");
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.isControlDown() && e.getKeyChar() == ' ') {
                    Set<String> commands = CommandRegistry.Instance.factories.keySet();
                    commands.stream().sorted().forEach(c -> {
                        JMenuItem item = new JMenuItem(c);
                        item.addActionListener(l -> {
                            field.insert(" " + c + ";", field.getCaretPosition());
                        });
                        add(item);
                    });
                    Point cp = field.getCaret().getMagicCaretPosition();
                    show(field, cp.x, cp.y);
                }
            }
        });
    }
    
}
