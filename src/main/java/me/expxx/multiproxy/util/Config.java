package me.expxx.multiproxy.util;

import dev.dejvokep.boostedyaml.YamlDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

public class Config {

    private static YamlDocument config;
    private static Path pluginDir;
    private static File config_location;

    public Config(Path dir) {
        pluginDir = dir;
        config_location = new File(pluginDir + "/config.yml");
        loadConfig();
    }

    public static void loadConfig() {
        try {
            config = YamlDocument.create(config_location, Objects.requireNonNull(Config.class.getClassLoader().getResourceAsStream("config.yml")));
        } catch(IOException ex) { ex.printStackTrace(); }
    }

    public static YamlDocument getConfig() {
        return config;
    }
}
