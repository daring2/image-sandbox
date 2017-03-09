package com.gitlab.daring.image.swing;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class NotificationUtils {

    public static void showErrorDialog(Component c, String msg) {
        JTextArea field = new JTextArea(10, 40);
        field.setLineWrap(true);
        field.setEditable(false);
        field.setText(msg);
        showMessageDialog(c, new JScrollPane(field), "Ошибка", ERROR_MESSAGE);
    }

    private NotificationUtils() {
    }

}
