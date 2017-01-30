package com.gitlab.daring.image.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigUtils {

	public static Config defaultConfig() {
		return ConfigFactory.load();
	}

	public static Config configFromString(String str) {
		return ConfigFactory.parseString(str);
	}

	public static int getIntOpt(Config c, String path, int defValue) {
		return c.hasPath(path) ? c.getInt(path) : defValue;
	}

	private ConfigUtils() {
	}

}
