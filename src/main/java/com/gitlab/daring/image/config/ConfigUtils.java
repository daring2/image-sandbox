package com.gitlab.daring.image.config;

import com.google.common.io.Files;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;

import java.io.File;
import java.util.Map;

import static com.gitlab.daring.image.util.CommonUtils.tryRun;
import static com.typesafe.config.ConfigFactory.parseFile;
import static com.typesafe.config.ConfigRenderOptions.concise;
import static java.nio.charset.StandardCharsets.UTF_8;

public class ConfigUtils {

    public static final String AppConfigFile = "config/application.conf";

    public static final Config defaultConfig = loadDefaultConfig();

    private static Config loadDefaultConfig() {
        File f = new File(AppConfigFile);
        Config c = f.exists() ? parseFile(f) : emptyConfig();
        return c.withFallback(ConfigFactory.load());
    }

    public static Config defaultConfig() {
        return defaultConfig;
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

    public static Config loadConfig(String path) {
        Config c = ConfigFactory.load(ConfigParseOptions.defaults());
        return path != null ? c.getConfig(path) : c;
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
        Config dc = loadConfig(null);
        Config sc = db.build(c.withFallback(dc), referenceConfig());
        saveConfig(sc, file);
    }

    public static int getIntOpt(Config c, String path, int defValue) {
        return c.hasPath(path) ? c.getInt(path) : defValue;
    }

    private ConfigUtils() {
    }

}
