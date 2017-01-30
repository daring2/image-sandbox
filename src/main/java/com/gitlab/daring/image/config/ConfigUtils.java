package com.gitlab.daring.image.config;

import com.google.common.io.Files;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import static com.typesafe.config.ConfigRenderOptions.concise;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ConfigUtils {

	public static Config defaultConfig() {
		return ConfigFactory.load();
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

	public static void saveConfig(Config c, String file) throws IOException {
		String str = c.root().render(concise().setFormatted(true).setJson(false));
		Files.write(str, new File(file), UTF_8);
	}

	public static int getIntOpt(Config c, String path, int defValue) {
		return c.hasPath(path) ? c.getInt(path) : defValue;
	}

	public static int getPercent(Config c, String path) {
		return (int) (c.getDouble(path) * 100);
	}

	private ConfigUtils() {
	}

}
