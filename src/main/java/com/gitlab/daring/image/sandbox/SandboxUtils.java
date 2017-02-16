package com.gitlab.daring.image.sandbox;

import com.typesafe.config.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static com.gitlab.daring.image.config.ConfigUtils.configFromMap;
import static com.gitlab.daring.image.config.ConfigUtils.saveDiffConfig;

public class SandboxUtils {

	public static void saveCompConfig(String path, Consumer<Map<String, Object>> cb) {
		Map<String, Object> m = new HashMap<>();
		cb.accept(m);
		Config c = configFromMap(m).atPath(path);
		saveDiffConfig(c, "conf/application.conf");
	}

	private SandboxUtils() {
	}
}
