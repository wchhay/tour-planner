package at.technikum.tourplanner.config;

public class AppConfigurationLoader {

    private static AppConfigurationLoader instance;

    private final ConfigReader configReader;

    private AppConfigurationLoader() {
        configReader = new PropertiesConfigReader();
    }

    public static AppConfigurationLoader getInstance() {
        if (null == instance) {
            instance = new AppConfigurationLoader();
        }
        return instance;
    }

    public AppConfiguration getAppConfiguration() {
        return configReader.getAppConfiguration();
    }
}
