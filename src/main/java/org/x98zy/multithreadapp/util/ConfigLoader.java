package org.x98zy.multithreadapp.util;

import org.x98zy.multithreadapp.exception.FerrySimulationException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;

    public ConfigLoader() throws FerrySimulationException {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FerrySimulationException("Config file 'config.properties' not found in classpath");
            }
            properties.load(input);
        } catch (FerrySimulationException e) {
            throw e;
        } catch (Exception e) {
            throw new FerrySimulationException("Error loading configuration", e);
        }
    }

    public int getInt(String key) throws FerrySimulationException {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new FerrySimulationException("Missing configuration key: " + key);
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new FerrySimulationException("Invalid integer value for key: " + key, e);
        }
    }

    public double getDouble(String key) throws FerrySimulationException {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new FerrySimulationException("Missing configuration key: " + key);
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new FerrySimulationException("Invalid double value for key: " + key, e);
        }
    }

    public String getString(String key) throws FerrySimulationException {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new FerrySimulationException("Missing configuration key: " + key);
        }
        return value;
    }
}