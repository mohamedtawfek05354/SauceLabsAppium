package Reuse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static java.lang.invoke.MethodHandles.lookup;

public class ConfigLoader {
    public static final Logger log = LogManager.getLogger(lookup().lookupClass());
    private Properties properties;

    public ConfigLoader(String filepath) {
        log.info("Initializing ConfigLoader with file: {}", filepath);
        loadProperties(filepath);
    }

    private void loadProperties(String filepath) {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(filepath);
            properties.load(fileInputStream);
            log.info("Properties loaded successfully from: {}", filepath);
        } catch (IOException e) {
            log.error("Failed to load properties from file: {}", filepath, e);
        }
    }

    public String getProperty(String key) {
        log.info("Fetching property for key: {}", key);
        return properties.getProperty(key);
    }

    public String[] getArrayValue(String key) {
        log.info("Fetching array value for key: {}", key);
        String value = properties.getProperty(key);
        if (value != null) {
            return value.split(",", -1);
        }
        log.warn("Key {} not found, returning empty array", key);
        return new String[0];
    }
}