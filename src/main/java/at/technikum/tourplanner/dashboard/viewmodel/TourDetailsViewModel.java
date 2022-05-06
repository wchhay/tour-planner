package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourDetailsViewModel {

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setTour(Tour tour) {
        if (null != tour) {
            name.set(tour.name());
            description.set(tour.description());
        } else {
            name.set("");
            description.set("");
        }
    }
}
