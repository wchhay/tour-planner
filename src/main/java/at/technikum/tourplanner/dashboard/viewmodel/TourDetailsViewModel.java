package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.service.TourService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import static at.technikum.tourplanner.util.TimeConverterUtil.convertToTimeString;


public class TourDetailsViewModel {

    private Tour selectedTour;

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty transportType = new SimpleStringProperty();
    private final StringProperty distance = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();
    private final ObjectProperty<Image> tourMapImage = new SimpleObjectProperty<>();

    private final TourService tourService;

    public TourDetailsViewModel(TourService tourService) {
        this.tourService = tourService;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty fromProperty() {
        return from;
    }

    public StringProperty toProperty() {
        return to;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public StringProperty timeProperty() {
        return time;
    }

    public ObjectProperty<Image> tourMapImageProperty() {
        return tourMapImage;
    }


    public void setTour(Tour tour) {
        selectedTour = tour;
        if (null != tour) {
            name.set(tour.getName());
            from.set(tour.getFrom());
            to.set(tour.getTo());
            description.set(tour.getDescription());
            transportType.set(tour.getTransportType().value);
            distance.set(tour.getDistance().toString());
            time.set(convertToTimeString(tour.getEstimatedTime()));

            downloadImage();
        } else {
            name.set("");
            from.set("");
            to.set("");
            description.set("");
            transportType.set("");
            distance.set("");
            time.set("");
            tourMapImage.set(null);
        }
    }

    private void downloadImage() {
        Image image = tourService.downloadTourMapImage(selectedTour.getId());
        image.exceptionProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                Alert aLert = new Alert(Alert.AlertType.ERROR, "Cannot download image");
                aLert.showAndWait();
            }
        });
        tourMapImage.set(image);
    }
}
