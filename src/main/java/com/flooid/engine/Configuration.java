package com.flooid.engine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Properties;

public class Configuration {
    private static final Configuration instance = new Configuration();

    private final Properties properties;

    public Configuration() {
        properties = loadProperties();
    }

    private Properties loadProperties() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("configs/configuration.properties")).getFile());
            try (InputStream inputStream = Files.newInputStream(file.toPath())) {
                Properties properties = new Properties();
                properties.load(inputStream);
                return properties;
            }
        } catch (IOException e) {
            throw new RuntimeException("Configuration.properties not found at " + "configs/configuration.properties", e);
        }
    }

    public static Configuration getInstance() {
        return instance;
    }

    /**
     * Gets a property given a key. Check in system properties first and defaults to properties file if not found.
     */
    public String getProperty(String key) throws IllegalStateException {
        String property = System.getProperty(key);
        if (property == null) {
            property = properties.getProperty(key);
        }
        if (property == null) {
            throw new IllegalStateException(String.format("Required property %s is not set", key));
        } else if (property.startsWith("${")) {
            throw new IllegalStateException(String.format("Required property %s is not replaced (value: %s)", key, property));
        } else {
            return property;
        }
    }

    public String getProperty(String key, String defaultValue) {
        try {
            return getProperty(key);
        } catch (IllegalStateException e) {
            return defaultValue;
        }
    }
}
