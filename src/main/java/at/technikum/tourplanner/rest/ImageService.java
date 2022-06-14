package at.technikum.tourplanner.rest;

import javafx.scene.image.Image;

import java.util.UUID;

public interface ImageService {

    Image downloadTourMapImage(UUID tourId);
}
