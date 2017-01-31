package com.gitlab.daring.image.swing;

import javax.swing.*;
import java.awt.*;

public class BaseFrame extends JFrame {

	public BaseFrame(String title, Container content) {
		super(title);
		setContentPane(content);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
	}

}
