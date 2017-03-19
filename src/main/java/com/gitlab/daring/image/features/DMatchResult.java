package com.gitlab.daring.image.features;

import org.bytedeco.javacpp.opencv_core.DMatch;
import org.bytedeco.javacpp.opencv_core.DMatchVector;

import java.util.List;

import static com.gitlab.daring.image.util.OpencvConverters.toJava;

public class DMatchResult {

    public final KeyPointList points1;
    public final KeyPointList points2;
    public final List<DMatch> matches;

    public DMatchResult(KeyPointList points1, KeyPointList points2, List<DMatch> matches) {
        this.points1 = points1;
        this.points2 = points2;
        this.matches = matches;
    }

    public DMatchResult(KeyPointList points1, KeyPointList points2, DMatchVector mv) {
        this(points1, points2, toJava(mv));
    }
}