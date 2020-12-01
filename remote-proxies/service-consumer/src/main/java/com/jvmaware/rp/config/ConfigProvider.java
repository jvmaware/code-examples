package com.jvmaware.rp.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.InputStream;

public class ConfigProvider {
    private final String configName = "application.yml";
    private final ApiConfig config;

    public ConfigProvider() {
        Yaml yaml = new Yaml(new Constructor(ApiConfig.class));
        yaml.setBeanAccess(BeanAccess.FIELD);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(configName);
        config = yaml.load(inputStream);
    }

    public ApiConfig getConfig() {
        return config;
    }
}
