package at.technikum.tourplanner.rest;

import at.technikum.tourplanner.config.AppConfiguration;
import javafx.scene.image.Image;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@Log4j2
public class ImageDownloadService implements ImageService {

    private final AppConfiguration appConfiguration;

    public ImageDownloadService(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @Override
    public Image downloadTourMapImage(UUID tourId) {
        String baseUrl = appConfiguration.getRestBaseUrl();

        return new Image(baseUrl + "/tours/" + tourId + "/map", true);
    }
}
