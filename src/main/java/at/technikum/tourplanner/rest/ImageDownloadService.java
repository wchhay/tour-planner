package at.technikum.tourplanner.rest;

import at.technikum.tourplanner.config.ConfigService;
import javafx.scene.image.Image;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@Log4j2
public class ImageDownloadService implements ImageService {

    private final ConfigService configService;

    public ImageDownloadService(ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public Image downloadTourMapImage(UUID tourId) {
        String baseUrl = configService.getKey("rest-base-url");

        return new Image(baseUrl + "/tours/" + tourId + "/map", true);
    }
}
