package com.gitlab.daring.sandbox.image.common;

import com.typesafe.config.Config;
import org.slf4j.Logger;
import static com.gitlab.daring.sandbox.image.util.ConfigUtils.defaultConfig;
import static org.slf4j.LoggerFactory.getLogger;

@SuppressWarnings("WeakerAccess")
public abstract class BaseComponent {

	protected final Logger log = getLogger(getClass());

	public final Config config;

	protected BaseComponent(Config config) {
		this.config = config;
	}

	protected BaseComponent(String configPath) {
		this(defaultConfig().getConfig(configPath));
	}

	protected Config getConfig(String path) {
		return config.getConfig(path);
	}

}