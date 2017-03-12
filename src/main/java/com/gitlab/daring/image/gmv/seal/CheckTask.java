package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandEnv;
import com.gitlab.daring.image.command.CommandScript;
import org.bytedeco.javacpp.opencv_core.Mat;

import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;

import static com.gitlab.daring.image.util.ImageUtils.centerRect;
import static com.gitlab.daring.image.util.ImageUtils.cropCenter;
import static org.bytedeco.javacpp.opencv_imgproc.warpAffine;
import static org.bytedeco.javacpp.opencv_imgproc.warpPerspective;

@NotThreadSafe
class CheckTask {

    final SealCheckService srv;
    final SealCheckParams params;
    final CommandScript script;
    final CommandEnv env;
    final Mat m1, m2, marker;
    final Rectangle objRect;

    CheckTask(SealCheckService srv) {
        this.srv = srv;
        params = srv.params;
        script = srv.script;
        env = script.env;
        m1 = loadMat(params.sampleFile.v, "m1");
        m2 = loadMat(params.shotFile.v, "m2");
        marker = loadMarkerMat(params.markerFile.v);
        objRect = centerRect(m2, 1 / params.cropSize);
    }

    Mat loadMat(String file, String name) {
        env.vars.put("file", file);
        env.vars.put("name", name);
        Mat m = runLoadTask("load");
        env.putMat(name, m);
        return m;
    }

    Mat loadMarkerMat(String file) {
        if (file.isEmpty()) return new Mat();
        env.vars.put("file", file);
        return runLoadTask("loadMarker");
    }

    Mat runLoadTask(String task) {
        Mat m = script.runTask(task, null);
        if (m.empty()) return m;
        m = script.runTask("postLoad", m);
        m = cropCenter(m, params.objSize.pv() * params.cropSize);
        return m;
    }

    void run() {
        Mat tm = new TransformBuilder(this).build();
        Mat tm2 = transformTarget(tm);
        Mat dm = new DiffBuilder(this).build(tm2);
        showMat(dm, "Различия");
    }

    Mat transformTarget(Mat tm) {
//        System.out.println("tm = " + tm.createIndexer());
        Mat rm = new Mat();
        if (tm.rows() == 3) warpPerspective(m2, rm, tm, m2.size()); else warpAffine(m2, rm, tm, m2.size());
        return rm;
    }

    void showMat(Mat m, String title) {
        env.mat = m;
        script.runCommand("show", title);
    }

}