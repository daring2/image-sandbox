package com.gitlab.daring.image.gmv.assistant;

import java.util.HashMap;
import java.util.Map;

import static com.gitlab.daring.image.config.ConfigUtils.configFromMap;
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
        if (isNotBlank(file)) {
            imwrite(file, sa.getInputMat());
            checkShot();
        }
    }

    void checkShot() {
        Map<String, Object> params = new HashMap<>();
        params.put("sampleFile", sa.sampleFile.v);
        params.put("shotFile", sa.shotFile.v);
        params.put("objSize", sa.positionControl.objSize.v);
        sa.sealCheckSandbox.apply(configFromMap(params));
    }

}