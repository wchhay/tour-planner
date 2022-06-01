package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class TourDetailsViewModel {

    private Tour selectedTour;
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty transportType = new SimpleStringProperty();
    private final StringProperty distance = new SimpleStringProperty();
    private final StringProperty time = new SimpleStringProperty();

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

    public void setTour(Tour tour) {
        selectedTour = tour;
        if (null != tour) {
            name.set(tour.getName());
            from.set(tour.getFrom());
            to.set(tour.getTo());
            description.set(tour.getDescription());
            transportType.set(tour.getTransportType());
        } else {
            name.set("");
            from.set("");
            to.set("");
            description.set("");
            transportType.set("");
        }

        // TODO: get values from backend
        distance.set("");
        time.set("");
    }

    public void updateTour() {
        if (null != selectedTour) {
            selectedTour.setName(name.get());
            selectedTour.setName(from.get());
            selectedTour.setName(to.get());
            selectedTour.setDescription(description.get());
            selectedTour.setDescription(transportType.get());
            selectedTour.setDescription(distance.get());
            selectedTour.setDescription(time.get());
        }
    }
}
