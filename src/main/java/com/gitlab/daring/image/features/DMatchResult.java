package com.gitlab.daring.image.features;

import org.bytedeco.javacpp.opencv_core.DMatchVector;

public class DMatchResult {

    public final KeyPointList points1;
    public final KeyPointList points2;
    public final DMatchVector matchVector = new DMatchVector();

    public DMatchResult(KeyPointList points1, KeyPointList points2) {
        this.points1 = points1;
        this.points2 = points2;
    }

}