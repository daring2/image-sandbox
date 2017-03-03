package com.gitlab.daring.image.command.parameter;

import one.util.streamex.StreamEx;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.gitlab.daring.image.swing.SwingUtils.newButton;

class ParamFileChooser extends JFileChooser {

	public final FileParam param;
	public final JTextField valueField;
	public final JButton openButton;

	public ParamFileChooser(FileParam param, JTextField valueField) {
		this.param = param;
		this.valueField = valueField;
		openButton = newButton("...", this::open);
		setCurrentDirectory(new File("."));
		setMultiSelectionEnabled(param.maxCount != 1);
		setFileFilter(createFileFilter());
	}

	FileFilter createFileFilter() {
		String[] exts = {"bmp", "png", "jpg", "jpeg", "gif"};
		return new FileNameExtensionFilter("Images", exts);
	}

	public void open() {
		int r = showOpenDialog(valueField);
		if (r == APPROVE_OPTION) {
			String fl = buildFileList();
			valueField.setText(fl);
			valueField.postActionEvent();
		}
	}

	String buildFileList() {
		Path d = Paths.get("").toAbsolutePath();
		File[] fs = getSelectedFiles();
		return StreamEx.of(fs).map(File::toPath).map(d::relativize).joining(",");
	}

}
