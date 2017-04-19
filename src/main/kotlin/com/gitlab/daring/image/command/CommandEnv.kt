package com.gitlab.daring.image.command

import com.gitlab.daring.image.command.structure.Contour
import com.gitlab.daring.image.features.DMatchResult
import org.bytedeco.javacpp.opencv_core.KeyPointVector
import org.bytedeco.javacpp.opencv_core.Mat
import org.bytedeco.javacpp.opencv_features2d.DescriptorMatcher
import org.bytedeco.javacpp.opencv_features2d.Feature2D
import java.util.*

class CommandEnv {

    var task = ""
        set(v) {
            field = v; curTask = ""
        }

    var curTask = ""

    var mat = Mat()
    var mats = HashMap<String, Mat>()
    var vars = HashMap<String, Any>()
    var contours = emptyList<Contour>()

    var featureDetector: Feature2D? = null
    var keyPoints = KeyPointVector()
    var descriptorMatcher: DescriptorMatcher? = null
    var matchResult: DMatchResult? = null

    fun getMat(key: String): Mat {
        val k = eval(key)
        return if (k.isEmpty()) mat else mats[k]!!.clone()
    }

    fun putMat(key: String, m: Mat): CommandEnv {
        val k = eval(key)
        val mc = m.clone()
        if (k.isEmpty()) mat = mc else mats.put(k, mc)
        return this
    }

    fun <T> getVar(key: String): T {
        return vars[key] as T
    }

    fun putVar(key: String, value: Any): CommandEnv {
        return apply { vars.put(key, value) }
    }

    fun eval(exp: String): String {
        val vn = if (exp.startsWith("$")) exp.substring(1) else ""
        return if (vn.isEmpty()) exp else "" + vars[vn]
    }

}
