package com.gitlab.daring.sandbox.image.common;

import com.typesafe.config.Config;
import org.slf4j.Logger;
import static com.gitlab.daring.sandbox.image.util.ConfigUtils.defaultConfig;
import static org.slf4j.LoggerFactory.getLogger;

@SuppressWarnings("WeakerAccess")
public class BaseComponent {

	protected final Logger log = getLogger(getClass());

	protected final Config config;

	public BaseComponent(String configPath) {
		this.config =  defaultConfig().getConfig(configPath);
	}

}
