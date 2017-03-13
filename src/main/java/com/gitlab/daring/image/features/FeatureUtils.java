package com.gitlab.daring.image.features;

import org.bytedeco.javacpp.indexer.FloatIndexer;
import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_features2d.DescriptorMatcher;
import org.bytedeco.javacpp.opencv_features2d.Feature2D;

import static org.bytedeco.javacpp.opencv_calib3d.RANSAC;
import static org.bytedeco.javacpp.opencv_calib3d.findHomography;
import static org.bytedeco.javacpp.opencv_core.*;

public class FeatureUtils {
    
    public static KeyPointList detectAndCompute(Feature2D d, Mat m) {
        KeyPointList r = new KeyPointList();
        d.detectAndCompute(m, new Mat(), r.points, r.descriptors);
        return r;
    }

    public static Mat buildHomography(Feature2D fd, DescriptorMatcher dm, Mat m1, Mat m2) {
        KeyPointList ps1 = detectAndCompute(fd, m1);
        KeyPointList ps2 = detectAndCompute(fd, m2);
        DMatchVector mv = new DMatchVector();
        dm.match(ps1.descriptors, ps2.descriptors, mv);
//        Mat m3 = buildMat(r -> drawMatches(m1, ps1.points, m2, ps2.points, mv, r)); showMat(m3, "matches");
        return findHomography(
            buildMatchPoints(mv, ps1.points, true),
            buildMatchPoints(mv, ps2.points, false),
            new Mat(), RANSAC, 3
        );
    }

    public static Mat buildMatchPoints(DMatchVector mv, KeyPointVector kps, boolean qps) {
        int size = (int) mv.size();
        Mat pm = new Mat(1, size, CV_32FC2);
        FloatIndexer ind = pm.createIndexer();
        for (int i = 0; i < size; i++) {
            DMatch dm = mv.get(i);
            KeyPoint kp = kps.get(qps ? dm.queryIdx() : dm.trainIdx());
            Point2f pt = kp.pt(); ind.put(0, i, 0, pt.x()); ind.put(0, i, 1, pt.y());
        }
        return pm;
    }

    private FeatureUtils() {
    }

}
