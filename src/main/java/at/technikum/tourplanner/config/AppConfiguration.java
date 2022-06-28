package at.technikum.tourplanner.config;

import lombok.Builder;
import lombok.Data;

import java.util.Properties;

@Data
@Builder
public class AppConfiguration {
    private String restBaseUrl;
    private String defaultExportDir;

    public static AppConfiguration fromProperties(Properties properties) {
        return AppConfiguration.builder()
                .restBaseUrl(properties.getProperty("rest-base-url"))
                .defaultExportDir(properties.getProperty("default-export-dir"))
                .build();
    }
}
