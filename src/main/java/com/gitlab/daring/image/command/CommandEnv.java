package com.gitlab.daring.image.command;

import org.bytedeco.javacpp.opencv_core.Mat;

import java.util.HashMap;
import java.util.Map;

public class CommandEnv {

	public Mat mat = new Mat();
	public Map<String, Mat> mats = new HashMap<>();

}
