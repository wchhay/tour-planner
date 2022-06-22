package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Listener;
import at.technikum.tourplanner.dashboard.viewmodel.observer.Observable;
import at.technikum.tourplanner.rest.ImageService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.util.UUID;

import static at.technikum.tourplanner.util.TimeConverterUtil.convertToTimeString;


public class TourDetailsViewModel {

    private final ObjectProperty<UUID> id = new SimpleObjectProperty<>();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty transportType = new SimpleStringProperty();
    private final StringProperty distance = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();
    private final ObjectProperty<Image> tourMapImage = new SimpleObjectProperty<>();
    private final StringProperty popularity = new SimpleStringProperty();
    private final StringProperty childFriendliness = new SimpleStringProperty();

    private final Observable<Boolean> tourEditClickedObservable = new Observable<>();

    private final ImageService imageService;

    public TourDetailsViewModel(ImageService imageService) {
        this.imageService = imageService;
    }

    public ObjectProperty<UUID> idProperty() {
        return id;
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

    public StringProperty popularityProperty() {
        return popularity;
    }

    public StringProperty childFriendlinessProperty() {
        return childFriendliness;
    }

    public void subscribeToTourEditClicked(Listener<Boolean> listener) {
        tourEditClickedObservable.subscribe(listener);
    }

    public void unsubscribeFromTourEditClicked(Listener<Boolean> listener) {
        tourEditClickedObservable.unsubscribe(listener);
    }

    public void setTour(Tour tour) {
        if (null != tour) {
            id.set(tour.getId());
            name.set(tour.getName());
            from.set(tour.getFrom());
            to.set(tour.getTo());
            description.set(tour.getDescription());
            transportType.set(tour.getTransportType().value);
            distance.set(convertNullableNumberToString(tour.getDistance()));
            time.set(convertToTimeString(tour.getEstimatedTime()));
            popularity.set(convertNullableNumberToString(tour.getPopularity()));
            childFriendliness.set(convertNullableNumberToString(tour.getChildFriendliness()));

            downloadImage();
        } else {
            id.set(null);
            name.set("");
            from.set("");
            to.set("");
            description.set("");
            transportType.set("");
            distance.set("");
            time.set("");
            tourMapImage.set(null);
            popularity.set("");
            childFriendliness.set("");
        }
    }

    public void editTour() {
        tourEditClickedObservable.notifyListeners(true);
    }

    private String convertNullableNumberToString(Number number) {
        if (null != number) {
            return number.toString();
        }
        return "";
    }

    private void downloadImage() {
        if (id.isNotNull().get()) {
            Image image = imageService.downloadTourMapImage(id.get());
            image.exceptionProperty().addListener((observable, oldValue, newValue) -> {
                if (null != newValue) {
                    Alert aLert = new Alert(Alert.AlertType.ERROR, "Cannot download image");
                    aLert.showAndWait();
                }
            });
            tourMapImage.set(image);
        }
    }
}
