package com.gitlab.daring.image.command;

import com.gitlab.daring.image.command.structure.Contour;
import com.gitlab.daring.image.features.DMatchResult;
import org.bytedeco.javacpp.opencv_core.KeyPointVector;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_features2d.DescriptorMatcher;
import org.bytedeco.javacpp.opencv_features2d.Feature2D;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandEnv {

    public String task = "";
    public String curTask = "";

    public Mat mat = new Mat();
    public Map<String, Mat> mats = new HashMap<>();
    public Map<String, Object> vars = new HashMap<>();
    public List<Contour> contours;

    public Feature2D featureDetector;
    public KeyPointVector keyPoints = new KeyPointVector();
    public DescriptorMatcher descriptorMatcher;
    public DMatchResult matchResult;

    public void setTask(String task) {
        this.task = task; curTask = "";
    }

    public Mat getMat(String key) {
        String k = eval(key);
        return k.isEmpty() ? mat : mats.get(k).clone();
    }

    public void putMat(String key, Mat m) {
        String k = eval(key);
        Mat mc = m.clone();
        if (k.isEmpty()) mat = mc; else mats.put(k, mc);
    }

    public CommandEnv putVar(String key, Object value) {
        vars.put(key, value); return this;
    }

    public String eval(String exp) {
        String vn = exp.startsWith("$") ? exp.substring(1) : "";
        return vn.isEmpty() ? exp : "" + vars.get(vn);
    }

}
