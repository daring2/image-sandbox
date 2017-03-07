package com.gitlab.daring.image.gmv.assistant;

import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.common.BaseComponent;
import com.gitlab.daring.image.template.MatchResult;
import com.gitlab.daring.image.template.TemplateMatcher;
import com.google.common.primitives.Doubles;
import org.bytedeco.javacpp.opencv_core.Mat;

import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;

import static com.gitlab.daring.image.util.GeometryUtils.getCenterRect;
import static com.gitlab.daring.image.util.ImageUtils.*;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static java.lang.String.format;

@NotThreadSafe
class PositionControl extends BaseComponent {

    final ShotAssistant assistant;
    final IntParam objSize = new IntParam("0:Размер объекта:0-100");
    final TemplateMatcher matcher = new TemplateMatcher(getConfig("matcher"));
    final PositionLimits limits = new PositionLimits(getConfig("limits"));
    final IntParam minValue = new IntParam("0:Совпадение:0-100");

    Mat template;
    Rectangle objRect;
    Rectangle pos;
    double templateLimit;

    PositionControl(ShotAssistant a) {
        super(a.config.getConfig("position"));
        assistant = a;
        objSize.bind(a.config, "position.objSize");
        minValue.bind(a.config, "position.limits.minValue");
        objSize.onChange(this::updateObjSize);
        updateObjSize();
    }

    void updateObjSize() {
        objRect = getCenterRect(toJava(assistant.getSize()), objSize.pv());
        pos = limits.buildPositionRect(objRect.x, objRect.y);
    }

    void setSample(Mat mat) {
        template = buildTemplate(mat);
        MatchResult r1 = findMatch(resizeMat(mat, limits.scale));
        MatchResult r2 = findMatch(rotateMat(mat, limits.angle));
        templateLimit = Doubles.max(r1.value, r2.value);
    }

    Mat buildTemplate(Mat mat) {
        return assistant.templateBuilder.build(mat);
    }

    MatchResult findMatch(Mat mat) {
        Mat sm = buildTemplate(mat);
        return matcher.findBest(sm, cropMat(template, objRect));
    }

    boolean check(Mat mat) {
        if (template == null) return false;
        MatchResult mr = findMatch(mat);
        double mv = Double.max(minValue.pv(), templateLimit);
        String statusText = format("Совпадение: текущее %.3f, лимит %.3f", mr.value, mv);
        assistant.statusField.setText(statusText); //TODO refactor
        return mr.value > mv && pos.contains(mr.point);
    }

}
