package at.technikum.tourplanner.config;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Log4j2
public class ConfigServiceImpl implements ConfigService {

    public static final String APP_PROPERTIES = "app.properties";
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
            log.info("Loading app configurations from " + APP_PROPERTIES);
            properties.load(ConfigServiceImpl.class.getResourceAsStream(APP_PROPERTIES));
        } catch (IOException e) {
            log.error("Cannot read configurations from " + APP_PROPERTIES);
        }
    }
}
