package com.gitlab.daring.sandbox.image.processor;

import org.bytedeco.javacpp.opencv_core.Mat;
import java.util.List;

public class CompositeProcessor implements ImageProcessor{

	final List<ImageProcessor> processors;

	public CompositeProcessor(List<ImageProcessor> processors) {
		this.processors = processors;
	}

	@Override
	public Mat process(Mat mat) {
		for (ImageProcessor p : processors)
			mat = p.process(mat);
		return mat;
	}

}
