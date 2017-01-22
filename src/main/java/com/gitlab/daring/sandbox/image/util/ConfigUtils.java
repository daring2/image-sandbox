package com.gitlab.daring.sandbox.image.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ConfigUtils {

	public static Config defaultConfig() {
		return ConfigFactory.load();
	}

	private ConfigUtils() {
	}

}
