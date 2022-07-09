package at.technikum.tourplanner.service.tour;

import at.technikum.tourplanner.config.AppConfiguration;
import javafx.scene.image.Image;

import java.util.UUID;

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
