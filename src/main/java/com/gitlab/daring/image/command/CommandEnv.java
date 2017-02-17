package com.gitlab.daring.image.command;

import com.gitlab.daring.image.command.structure.Contour;
import org.bytedeco.javacpp.opencv_core.KeyPointVector;
import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandEnv {

	public String task = "";
	public String curTask = "";

	public Mat mat = new Mat();
	public Map<String, Mat> mats = new HashMap<>();
	public Map<String, Object> vars = new HashMap<>();
	public KeyPointVector keyPoints = new KeyPointVector();
	public List<Contour> contours;

	public void setTask(String task) {
		this.task = task; curTask = "";
	}

	public Mat getMat(String key) {
		String k = eval(key);
		return k.isEmpty() ? mat : mats.get(k);
	}

	public void putMat(String key, Mat m) {
		mats.put(eval(key), m.clone());
	}

	public String eval(String exp) {
		String vn = exp.startsWith("$") ? exp.substring(1) : "";
		return vn.isEmpty() ? exp: "" + vars.get(vn);
	}

}
