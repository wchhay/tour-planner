package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class TourDetailsViewModel {

    private Tour selectedTour;
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setTour(Tour tour) {
        selectedTour = tour;
        if (null != tour) {
            name.set(tour.getName());
            description.set(tour.getDescription());
        } else {
            name.set("");
            description.set("");
        }
    }

    public void updateTour() {
        if (null != selectedTour) {
            selectedTour.setName(name.get());
            selectedTour.setDescription(description.get());
        }
    }
}
