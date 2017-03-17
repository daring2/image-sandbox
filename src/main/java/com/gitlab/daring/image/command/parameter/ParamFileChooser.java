package com.gitlab.daring.image.command.parameter;

import one.util.streamex.StreamEx;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.gitlab.daring.image.swing.SwingUtils.newButton;
import static javax.swing.JFileChooser.APPROVE_OPTION;

class ParamFileChooser {

    public final FileParam param;
    public final JTextField valueField;
    public final JButton openButton;
    
    JFileChooser fileChooser;

    public ParamFileChooser(FileParam param, JTextField valueField) {
        this.param = param;
        this.valueField = valueField;
        openButton = newButton("...", this::open);
    }

    JFileChooser createFileChooser() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        fc.setMultiSelectionEnabled(param.maxCount != 1);
        fc.setFileFilter(createFileFilter());
        return fc;
    }

    FileFilter createFileFilter() {
        String[] exts = {"bmp", "png", "jpg", "jpeg", "gif"};
        return new FileNameExtensionFilter("Images", exts);
    }

    public void open() {
        if (fileChooser == null) fileChooser = createFileChooser();
        int r = fileChooser.showOpenDialog(valueField);
        if (r == APPROVE_OPTION) {
            String fl = buildFileList();
            valueField.setText(fl);
            valueField.postActionEvent();
        }
    }

    String buildFileList() {
        Path d = Paths.get("").toAbsolutePath();
        File[] fs = fileChooser.getSelectedFiles();
        return StreamEx.of(fs).map(File::toPath).map(d::relativize).joining(",");
    }

}
