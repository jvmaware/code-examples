package com.jvmaware.rp.config;

import java.util.Map;

public class ApiConfig {

    private String baseUrl;
    private Map<String, String> mappings;


    public String getBaseUrl() {
        return baseUrl;
    }

    public ApiConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public Map<String, String> getMappings() {
        return mappings;
    }

    public ApiConfig setMappings(Map<String, String> mappings) {
        this.mappings = mappings;
        return this;
    }

    @Override
    public String toString() {
        return "ApiConfig{" +
                "baseUrl='" + baseUrl + '\'' +
                ", mappings=" + mappings +
                '}';
    }
}
