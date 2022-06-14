package at.technikum.tourplanner.config;

import java.io.IOException;
import java.util.Properties;

public class ConfigServiceImpl implements ConfigService {

    private final Properties properties = new Properties();

    public ConfigServiceImpl() {
        loadProperties();
    }

    @Override
    public String getKey(String key) {
        return properties.getProperty(key);
    }

    private void loadProperties() {
        try {
            properties.load(ConfigServiceImpl.class.getResourceAsStream("app.properties"));
        } catch (IOException e) {
            // TODO: add log
        }
    }
}
