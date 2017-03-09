package com.gitlab.daring.image.swing;

import javax.swing.*;
import java.awt.*;

import static com.gitlab.daring.image.swing.SwingUtils.addWindowClosedListener;

public class BaseFrame extends JFrame {

    public BaseFrame(String title, Container content) {
        super(title);
        setContentPane(content);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
    }

    public void addCloseListener(Runnable l) {
        addWindowClosedListener(this, l);
    }

    public void show(int width, int height) {
        setSize(width, height);
        setVisible(true);
    }

}
