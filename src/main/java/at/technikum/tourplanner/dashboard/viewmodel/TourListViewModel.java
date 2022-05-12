package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourListViewModel extends Publisher<Tour> {

    private final ObservableList<Tour> tourList = FXCollections.observableArrayList();

    public void addTour() {
        Tour tour = new Tour("New Tour", "");
        tourList.add(tour);
    }

    public ObservableList<Tour> getTourList() {
        return tourList;
    }

    public ChangeListener<Tour> getChangeListener() {
        return ((observable, oldValue, newValue) -> notifyListeners(newValue));
    }
}
