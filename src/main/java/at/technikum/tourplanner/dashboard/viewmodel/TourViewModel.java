package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourViewModel {

    private final StringProperty tourName = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObservableList<Tour> tourList = FXCollections.observableArrayList();

    public void addTour() {
        Tour tour = new Tour("Tour name placeholder", "Description placeholder");
        tourList.add(tour);
    }

    public ObservableList<Tour> getTourList() {
        return tourList;
    }

    public StringProperty tourNameProperty() {
        return tourName;
    }

    public StringProperty descriptionProperty() {
        return description;
    }
}
