package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.template.MatchResult;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_features2d.BFMatcher;
import org.bytedeco.javacpp.opencv_xfeatures2d.SURF;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.gitlab.daring.image.features.FeatureUtils.buildHomography;
import static com.gitlab.daring.image.util.ImageUtils.*;
import static org.bytedeco.javacpp.opencv_imgproc.getAffineTransform;
import static org.bytedeco.javacpp.opencv_video.estimateRigidTransform;

class TransformBuilder {

    final CheckTask ct;
    final FindMethod method;

    final List<Point> ps1 = new ArrayList<>();
    final List<Point> ps2 = new ArrayList<>();

    TransformBuilder(CheckTask ct) {
        this.ct = ct;
        method = ct.params.findMethod.v;
    }

    Mat build() {
        Rectangle r = new Rectangle(ct.objRect);
        if (method == FindMethod.Simple) {
            findMatch(r);
            Point p1 = ps1.get(0), p2 = ps2.get(0);
            float[][] vs = {{1, 0, p1.x - p2.x}, {0, 1, p1.y - p2.y}};
            return newFloatMat(vs);
        } else if (method == FindMethod.Template) {
            r.setSize(r.width / 3, r.height / 3); findMatch(r);
            r.translate(r.width, 0); findMatch(r);
            r.translate(0, r.height); findMatch(r);
            return getAffineTransform(newPointArray(ps2), newPointArray(ps1));
        } else if (method == FindMethod.RigidTransform) {
            Mat[] ms = cropToMin(ct.m1, ct.m2);
            return estimateRigidTransform(ms[1], ms[0], ct.params.fullAffine);
        } else if (method == FindMethod.Homography) {
            return buildHomography(SURF.create(), new BFMatcher(), ct.m2, ct.m1);
        } else {
            throw new IllegalArgumentException("method=" + method);
        }
    }

    void findMatch(Rectangle r) {
        Mat tm = cropMat(ct.m1, r);
        MatchResult mr = ct.srv.matcher.findBest(ct.m2, tm);
        ps1.add(r.getLocation());
        ps2.add(mr.point);
    }

}