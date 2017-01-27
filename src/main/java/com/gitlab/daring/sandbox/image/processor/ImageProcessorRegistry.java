package com.gitlab.daring.sandbox.image.processor;

import com.gitlab.daring.sandbox.image.processor.impl.CannyProcessor;
import com.gitlab.daring.sandbox.image.processor.impl.MorphologyProcessor;
import com.gitlab.daring.sandbox.image.processor.impl.ThresholdProcessor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.split;

public class ImageProcessorRegistry {

	static final ImageProcessorRegistry Instance = new ImageProcessorRegistry();

	public static ImageProcessor parseProcList(String conf) {
		return Instance.parseList(conf);
	}

	final Map<String, ImageProcessor.Factory> registry = new HashMap<>();

	public ImageProcessorRegistry() {
		registry.put("morphology", MorphologyProcessor::new);
		registry.put("threshold", ThresholdProcessor::new);
		registry.put("canny", CannyProcessor::new);
	}

	public ImageProcessor parse(String conf) {
		String[] ss = split(conf, "()");
		String[] args = split(ss[1], ", ");
		return registry.get(ss[0].trim()).create(args);
	}

	public ImageProcessor parseList(String conf) {
		String[] ss = split(conf, ";\n");
		List<ImageProcessor> procs = Arrays.stream(ss).map(this::parse).collect(toList());
		return new CompositeProcessor(procs);
	}

}
