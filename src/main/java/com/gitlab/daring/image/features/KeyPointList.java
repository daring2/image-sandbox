package com.gitlab.daring.image.features;

import org.bytedeco.javacpp.opencv_core.KeyPointVector;
import org.bytedeco.javacpp.opencv_core.Mat;

public class KeyPointList {

    public final Mat mat;
    public final KeyPointVector points = new KeyPointVector();
    public final Mat descriptors = new Mat();

    public KeyPointList(Mat mat) {
        this.mat = mat;
    }
}
