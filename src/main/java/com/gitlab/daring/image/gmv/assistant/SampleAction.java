package com.gitlab.daring.image.gmv.assistant;

import java.io.File;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

class SampleAction {

    final ShotAssistant sa;

    volatile boolean load = true;
    volatile boolean save;

    SampleAction(ShotAssistant sa) {
        this.sa = sa;
        sa.sampleFile.onChange(() -> load = true);
    }

    void process() {
        if (load) load();
        if (save) save();
    }

    void load() {
        load = false;
        String file = sa.sampleFile.v;
        if (new File(file).exists()) {
            imread(file).copyTo(sa.sampleMat);
            sa.applySample();
        }
    }

    void save() {
        save = false;
        sa.getInputMat().copyTo(sa.sampleMat);
        String file = sa.sampleFile.v;
        if (isNotBlank(file)) imwrite(file, sa.sampleMat);
        sa.applySample();
    }

}