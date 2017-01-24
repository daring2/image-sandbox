package com.gitlab.daring.sandbox.image.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigUtils {

	public static Config defaultConfig() {
		return ConfigFactory.load();
	}

	public static int getIntOpt(Config c, String path, int defValue) {
		return c.hasPath(path) ? c.getInt(path) : defValue;
	}

	private ConfigUtils() {
	}

}
