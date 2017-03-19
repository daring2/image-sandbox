package com.gitlab.daring.image.command.feature;

import com.gitlab.daring.image.command.CommandRegistry;
import org.bytedeco.javacpp.opencv_features2d;
import org.bytedeco.javacpp.opencv_features2d.Feature2D;
import org.bytedeco.javacpp.opencv_features2d.GFTTDetector;
import org.bytedeco.javacpp.opencv_features2d.SimpleBlobDetector;
import org.bytedeco.javacpp.opencv_xfeatures2d;
import org.bytedeco.javacpp.opencv_xfeatures2d.StarDetector;

public enum FeatureDetectorType {

    GIFT(GFTTDetector::create),
    SURF(opencv_xfeatures2d.SURF::create),
    SIFT(opencv_xfeatures2d.SIFT::create),
    ORB(opencv_features2d.ORB::create),
    STAR(StarDetector::create),
    MSER(opencv_features2d.MSER::create),
    SimpleBlob(SimpleBlobDetector::create),
    ;

    public static void register(CommandRegistry r) {
        r.register("newFeatureDetector", FeatureDetectorCommand::new);
        r.register("newGFTT", GFTTCommand::new);
        r.register("newSURF", SURFCommand::new);
        r.register("newSIFT", SIFTCommand::new);
        r.register("newORB", ORBCommand::new);
        r.register("newStarDetector", StarDetectorCommand::new);
        r.register("newMSER", MSERCommand::new);
        r.register("newSimpleBlobDetector", SimpleBlobDetectorCommand::new);
    }

    public final Factory factory;

    FeatureDetectorType(Factory factory) {
        this.factory = factory;
    }

    @FunctionalInterface
    interface Factory {
        Feature2D create();
    }

}