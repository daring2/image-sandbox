package com.gitlab.daring.image.gmv.assistant;

import com.gitlab.daring.image.command.parameter.IntParam;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatExpr;
import org.bytedeco.javacpp.opencv_core.Scalar;

import static com.gitlab.daring.image.util.ImageUtils.addWeightedMat;
import static com.gitlab.daring.image.util.ImageUtils.drawRect;
import static org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.BLUE;
import static org.bytedeco.javacpp.helper.opencv_core.AbstractScalar.GREEN;
import static org.bytedeco.javacpp.opencv_core.max;
import static org.bytedeco.javacpp.opencv_core.multiply;

class DisplayBuilder {

    final ShotAssistant a;
    final IntParam sampleOpacity = new IntParam("0:Образец:0-100");
    final IntParam templateOpacity = new IntParam("0:Контур:0-100");

    DisplayBuilder(ShotAssistant a) {
        this.a = a;
        sampleOpacity.bind(a.config, "display.sampleOpacity");
        templateOpacity.bind(a.config, "display.templateOpacity");
    }

    void build(Mat inputMat) {
        Mat dm = a.displayMat;
        inputMat.copyTo(dm);
        if (!a.templateMat.empty()) {
            addWeightedMat(dm, a.sampleMat, dm, sampleOpacity.pv());
            MatExpr dt = multiply(a.templateMat, templateOpacity.pv());
            max(dm, dt.asMat(), dm);
        } else {
            inputMat.copyTo(dm);
        }
        Scalar rc = a.checkResult ? GREEN : BLUE;
        drawRect(dm, a.positionControl.objRect, rc, 2);
    }

}
