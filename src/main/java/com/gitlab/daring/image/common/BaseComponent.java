package com.gitlab.daring.image.common;

import com.typesafe.config.Config;

import static com.gitlab.daring.image.config.ConfigUtils.defaultConfig;

public abstract class BaseComponent {

    public final Config config;

    protected BaseComponent(Config config) {
        this.config = config;
    }

    protected BaseComponent(String configPath) {
        this(defaultConfig(configPath));
    }

    protected Config getConfig(String path) {
        return config.getConfig(path);
    }

}
