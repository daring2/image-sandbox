package com.gitlab.daring.image.config;

import com.google.common.io.Files;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.util.Map;

import static com.gitlab.daring.image.util.CommonUtils.tryRun;
import static com.typesafe.config.ConfigRenderOptions.concise;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ConfigUtils {

	public static final String AppConfigFile = "conf/application.conf";

	public static Config defaultConfig() {
		return ConfigFactory.load();
	}

	public static Config defaultConfig(String path) {
		return defaultConfig().getConfig(path);
	}

	public static Config referenceConfig() {
		return ConfigFactory.defaultReference();
	}

	public static Config emptyConfig() {
		return ConfigFactory.empty();
	}

	public static Config configFromMap(Map<String, Object> map) {
		return ConfigFactory.parseMap(map);
	}

	public static Config configFromString(String str) {
		return ConfigFactory.parseString(str);
	}

	public static void saveConfig(Config c, String file) {
		tryRun(() -> {
			String str = c.root().render(concise().setFormatted(true).setJson(false));
			Files.write(str, new File(file), UTF_8);
		});
	}

	public static void saveDiffConfig(Config c, String file) {
		ConfigDiffBuilder db = new ConfigDiffBuilder();
		Config dc = db.build(c.withFallback(defaultConfig()), referenceConfig());
		saveConfig(dc, file);
	}

	public static int getIntOpt(Config c, String path, int defValue) {
		return c.hasPath(path) ? c.getInt(path) : defValue;
	}

	private ConfigUtils() {
	}

}
