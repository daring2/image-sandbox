package com.gitlab.daring.image.command;

import com.gitlab.daring.image.command.structure.Contour;
import org.bytedeco.javacpp.opencv_core.KeyPointVector;
import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandEnv {

	public Mat mat = new Mat();
	public Map<String, Mat> mats = new HashMap<>();
	public Map<String, Object> vars = new HashMap<>();
	public KeyPointVector keyPoints = new KeyPointVector();
	public List<Contour> contours;

	public Mat getMat(String key) {
		return key.isEmpty() ? mat : mats.get(key);
	}

	public void setMat(String key, Mat m) {
		if (key.isEmpty()) mat = m; else mats.put(key, m);
	}

	public <T> T getVar(String key) {
		return (T) vars.get(key);
	}

}
