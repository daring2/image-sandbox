package com.gitlab.daring.image.gmv.assistant;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;

class ShotAction {

    final ShotAssistant sa;

    volatile boolean save;

    ShotAction(ShotAssistant sa) {
        this.sa = sa;
    }

    void process() {
        if (save) save();
    }

    void save() {
        save = false;
        String file = sa.shotFile.v;
        if (isNotBlank(file)) imwrite(file, sa.getInputMat());
    }

}