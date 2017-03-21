package com.gitlab.daring.image.command.parameter;

import java.io.File;

import static java.lang.Integer.parseInt;

public class FileParam extends StringParam {

    public int maxCount = 1;

    public FileParam(String sv) {
        super(sv);
        parseSpec();
    }

    @Override
    protected String[] parseArgs(String sv) {
        boolean isFile = new File(sv).exists();
        return isFile ? new String[]{sv} : super.parseArgs(sv);
    }

    private void parseSpec() {
        if (spec.isEmpty()) return;
        maxCount = parseInt(spec);
    }

}