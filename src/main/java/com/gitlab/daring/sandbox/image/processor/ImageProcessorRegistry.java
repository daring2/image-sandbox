package com.gitlab.daring.sandbox.image.processor;

import com.typesafe.config.Config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gitlab.daring.sandbox.image.processor.ImageProcessorUtils.parseArgs;
import static com.gitlab.daring.sandbox.image.util.ConfigUtils.defaultConfig;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.split;

public class ImageProcessorRegistry {

	static final ImageProcessorRegistry Instance = new ImageProcessorRegistry();

	public static ImageProcessor parseProcList(String conf) {
		return Instance.parseList(conf);
	}

	final Config pc = defaultConfig().getConfig("gmv.imageProcessors");
	final Map<String, ImageProcessor.Factory> registry = new HashMap<>();

	public ImageProcessorRegistry() {
		TransformProcessors.register(this);
	}

	public void register(String name, ImageProcessor.Factory f) {
		registry.put(name, f);
	}

	public ImageProcessor parse(String conf) {
		String[] ss = split(conf, "()");
		String name = ss[0].trim();
		String[] args = parseArgs(ss[1], getDefArgs(name));
		return registry.get(name).create(args);
	}

	private List<String> getDefArgs(String name) {
		return pc.hasPath(name) ? pc.getStringList(name) : emptyList();
	}

	public ImageProcessor parseList(String conf) {
		String[] ss = split(conf, ";\n");
		List<ImageProcessor> procs = Arrays.stream(ss).map(this::parse).collect(toList());
		return new CompositeProcessor(procs);
	}

}
