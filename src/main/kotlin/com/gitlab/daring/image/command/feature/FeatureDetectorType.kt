package com.gitlab.daring.image.command.feature

import com.gitlab.daring.image.command.CommandRegistry
import org.bytedeco.javacpp.opencv_features2d
import org.bytedeco.javacpp.opencv_features2d.*
import org.bytedeco.javacpp.opencv_xfeatures2d
import org.bytedeco.javacpp.opencv_xfeatures2d.StarDetector

internal enum class FeatureDetectorType (val create: () -> Feature2D) {

    GIFT(GFTTDetector::create),
    SURF(opencv_xfeatures2d.SURF::create),
    SIFT(opencv_xfeatures2d.SIFT::create),
    ORB(opencv_features2d.ORB::create),
    STAR(StarDetector::create),
    MSER(opencv_features2d.MSER::create),
    SimpleBlob(SimpleBlobDetector::create),
    ;

    companion object {

        fun register(r: CommandRegistry) {
            r.register("newFeatureDetector", ::FeatureDetectorCommand);
            r.register("newGFTT", ::GFTTCommand);
            r.register("newSURF", ::SURFCommand);
            r.register("newSIFT", ::SIFTCommand);
            r.register("newORB", ::ORBCommand);
            r.register("newStarDetector", ::StarDetectorCommand);
            r.register("newMSER", ::MSERCommand);
            r.register("newSimpleBlobDetector", ::SimpleBlobDetectorCommand);
        }

    }

}