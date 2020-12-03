package com.jvmaware.rp.config;

import java.util.Map;

public class ApiConfig {

    private String baseUrl;
    private Map<String, Mapping> mappings;


    public String getBaseUrl() {
        return baseUrl;
    }

    public ApiConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public Map<String, Mapping> getMappings() {
        return mappings;
    }

    public ApiConfig setMappings(Map<String, Mapping> mappings) {
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

    public static class Mapping {
        private String method;
        private String endPoint;

        public String getMethod() {
            return method;
        }

        public Mapping setMethod(String method) {
            this.method = method;
            return this;
        }

        public String getEndPoint() {
            return endPoint;
        }

        public Mapping setEndPoint(String endPoint) {
            this.endPoint = endPoint;
            return this;
        }
    }
}
