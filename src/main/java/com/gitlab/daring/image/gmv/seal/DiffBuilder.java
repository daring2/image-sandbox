package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.CommandScript;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Scalar;

import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.gitlab.daring.image.util.ImageUtils.convertToGrey;
import static com.gitlab.daring.image.util.ImageUtils.cropMat;
import static com.gitlab.daring.image.util.OpencvConverters.toJava;
import static one.util.streamex.IntStreamEx.range;
import static org.bytedeco.javacpp.opencv_core.min;

@NotThreadSafe
class DiffBuilder {

    final CheckTask task;
    final CommandScript script;
    final CommandEnv env;

    int wo;
    Mat cm2, dm1, dm2;

    public DiffBuilder(CheckTask task) {
        this.task = task;
        script = task.script;
        env = script.env;
    }

    Mat build(Mat m2) {
        wo = task.params.winOffset.v;
        Rectangle r = new Rectangle(task.objRect);
        cm2 = cropMat(m2, r);
        if (!task.marker.empty())
            env.putMat("marker", cropMat(task.marker, r));
        dm1 = runPreDiff(task.m1, r, "dm1");
        r.translate(-wo / 2, -wo / 2);
        dm2 = runPreDiff(m2, r, "dm2");
        return runBuildDiff();
    }

    Mat runPreDiff(Mat m, Rectangle r, String name) {
        env.vars.put("name", name);
        env.vars.put("rect", r);
        return script.runTask("preDiff", cropMat(m, r));
    }

    Mat runBuildDiff() {
        Dimension d = toJava(dm1.size());
        Rectangle wr = new Rectangle(0, 0, d.width - wo, d.height - wo);
        env.putMat("dm1", cropMat(dm1, wr));
        List<Mat> dms = new ArrayList<>();
        range(wo + 1).forEach(x -> range(wo + 1).forEach(y -> {
            wr.setLocation(x, y);
            env.putMat("dm2", cropMat(dm2, wr));
            script.runTask("buildDiff");
            dms.add(env.mat.clone());
        }));
        Mat rm = new Mat(wr.height, wr.width, dm1.type(), Scalar.WHITE);
        dms.forEach(m -> min(rm, m, rm));
        env.putMat("cm2", cropMat(convertToGrey(cm2), wr)); //TODO refactor
        return script.runTask("postDiff", rm);
    }

}