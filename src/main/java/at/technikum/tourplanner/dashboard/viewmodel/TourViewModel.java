package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TourViewModel {

    private final ObservableList<Tour> tourList = FXCollections.observableArrayList();

    private final List<SelectionChangedListener> listeners = new ArrayList<>();

    public void addTour() {
        Tour tour = new Tour("Tour name placeholder", "Description placeholder");
        tourList.add(tour);
    }

    public ObservableList<Tour> getTourList() {
        return tourList;
    }

    public ChangeListener<Tour> getChangeListener() {
        return ((observable, oldValue, newValue) -> notifyListeners(newValue));
    }

    public void addSelectionChangedListener(SelectionChangedListener listener) {
        listeners.add(listener);
    }

    public void removeSelectionChangedListener(SelectionChangedListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(Tour tour) {
        listeners.forEach(listener -> listener.changeSelection(tour));
    }
}
