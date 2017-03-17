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
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

class ParamFileChooser {

    public final FileParam param;
    public final JTextField valueField;
    public final JButton openButton;
    
    JFileChooser fc;

    public ParamFileChooser(FileParam param, JTextField valueField) {
        this.param = param;
        this.valueField = valueField;
        openButton = newButton("...", this::open);
    }

    JFileChooser createFileChooser() {
        JFileChooser fc = new JFileChooser();
        File vf = new File(defaultIfBlank(param.v, "."));
        fc.setCurrentDirectory(vf.isFile() ? vf.getParentFile() : vf);
        fc.setMultiSelectionEnabled(param.maxCount != 1);
        fc.setFileFilter(createFileFilter());
        return fc;
    }

    FileFilter createFileFilter() {
        String[] exts = {"bmp", "png", "jpg", "jpeg", "gif"};
        return new FileNameExtensionFilter("Images", exts);
    }

    public void open() {
        if (fc == null) fc = createFileChooser();
        int r = fc.showOpenDialog(valueField);
        if (r == APPROVE_OPTION) {
            String fl = buildFileList();
            valueField.setText(fl);
            valueField.postActionEvent();
        }
    }

    String buildFileList() {
        Path d = Paths.get("").toAbsolutePath();
        File[] fs = fc.isMultiSelectionEnabled() ? fc.getSelectedFiles() : new File[] {fc.getSelectedFile()};
        return StreamEx.of(fs).map(File::toPath).map(d::relativize).joining(",");
    }

}
