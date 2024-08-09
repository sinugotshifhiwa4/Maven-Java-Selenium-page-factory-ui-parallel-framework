package com.adactin.components;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class EncryptCredentials {

    public static final String propertiesPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";

    public static void main(String[] args) {
        encryptAndSaveCredentials();
    }

    private static void encryptAndSaveCredentials() {
        Properties properties = loadProperties();

        String username = properties.getProperty("USER");
        String password = properties.getProperty("PASSWORD");
        String invalidUsername = properties.getProperty("INVALID_USER");
        String invalidPassword = properties.getProperty("INVALID_PASSWORD");


        assert username != null;
        String encryptedUsername = EncryptionUtil.encrypt(username);
        assert password != null;
        String encryptedPassword = EncryptionUtil.encrypt(password);
        assert invalidUsername != null;
        String encryptedInvalidUsername = EncryptionUtil.encrypt(invalidUsername);
        assert invalidPassword != null;
        String encryptedInvalidPassword = EncryptionUtil.encrypt(invalidPassword);

        // Update properties with encrypted values
        properties.setProperty("USER", encryptedUsername);
        properties.setProperty("PASSWORD", encryptedPassword);
        properties.setProperty("INVALID_USER", encryptedInvalidUsername);
        properties.setProperty("INVALID_PASSWORD", encryptedInvalidPassword);

        // Save the updated properties file
        try (FileOutputStream outputStream = new FileOutputStream(propertiesPath)) {
            properties.store(outputStream, null);
            System.out.println("Encryption is done on config properties.");
        } catch (IOException e) {
            ExceptionHandler.handleException("Could not save properties file", e);
        }
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(propertiesPath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            ExceptionHandler.handleException("Could not load properties file", e);
        }
        return properties;
    }
}
