package com.adactin.components;

import com.adactin.utils.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

    private static final Properties properties = new Properties();
    private static final String propertiesPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";

    static {

        try (FileInputStream inputStream = new FileInputStream(propertiesPath)) {
            properties.load(inputStream);

        } catch (IllegalArgumentException e) {
            LoggerFactory.error("Failed to load property key");
            throw e; // Re-throw to propagate the exception
        } catch (IOException e) {
            LoggerFactory.error("Failed to load property key");
            ExceptionHandler.handleException("loadProperty", e);
        } finally {
            LoggerFactory.info("Property key loaded successfully");
        }
    }

    public static synchronized String getPropertyByKey(String key) {

        String value = properties.getProperty(key);

        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Invalid specified key " + key + " on properties file.");
        }

        return value;
    }
}
