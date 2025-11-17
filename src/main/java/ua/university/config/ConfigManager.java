package ua.university.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigManager {
    private static final Logger logger = Logger.getLogger(ConfigManager.class.getName());
    private final Properties properties;

    public ConfigManager(String configFilePath) throws IOException {
        this.properties = new Properties();
        loadConfig(configFilePath);
    }

    private void loadConfig(String configFilePath) throws IOException {
        try (InputStream input = new FileInputStream(configFilePath)) {
            properties.load(input);
            logger.info("Configuration loaded successfully from: " + configFilePath);
        } catch (IOException e) {
            logger.severe("Failed to load configuration: " + e.getMessage());
            throw e;
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.warning("Invalid integer value for key " + key + ": " + value);
            return defaultValue;
        }
    }
}
