package com.gitlab.daring.image.features

import org.bytedeco.javacpp.indexer.FloatIndexer
import org.bytedeco.javacpp.opencv_core.*
import org.bytedeco.javacpp.opencv_features2d.Feature2D

object FeatureUtils {

    fun detectAndCompute(d: Feature2D, m: Mat): KeyPointList {
        val ps = KeyPointList(m)
        d.detectAndCompute(m, Mat(), ps.points, ps.descriptors)
        return ps
    }

    fun buildMatchPoints(mv: DMatchVector, kps: KeyPointVector, qps: Boolean): Mat {
        val size = mv.size().toInt()
        val pm = Mat(1, size, CV_32FC2)
        val ind = pm.createIndexer<FloatIndexer>()
        for (i in 0..size - 1) {
            val dm = mv.get(i.toLong())
            val ki = if (qps) dm.queryIdx() else dm.trainIdx()
            val kp = kps.get(ki.toLong())
            val pt = kp.pt()
            ind.put(0, i.toLong(), 0, pt.x())
            ind.put(0, i.toLong(), 1, pt.y())
        }
        return pm
    }

}