package at.technikum.tourplanner.config;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Log4j2
public class PropertiesConfigReader implements ConfigReader {

    public static final String APP_PROPERTIES = "app.properties";
    private final Properties properties = new Properties();

    @Override
    public AppConfiguration getAppConfiguration() {
        loadProperties();
        return AppConfiguration.fromProperties(properties);
    }

    private void loadProperties() {
        if (properties.isEmpty()) {
            try {
                logger.debug("Loading app configurations from {}", APP_PROPERTIES);
                properties.load(PropertiesConfigReader.class.getResourceAsStream(APP_PROPERTIES));
            } catch (IOException e) {
                logger.error("Cannot read configurations from {}", APP_PROPERTIES);
                System.exit(1);
            }
        }
    }
}
