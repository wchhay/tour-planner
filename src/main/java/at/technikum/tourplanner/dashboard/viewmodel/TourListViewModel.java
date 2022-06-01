package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;
import at.technikum.tourplanner.observer.Listener;
import at.technikum.tourplanner.observer.Observable;
import at.technikum.tourplanner.observer.Publisher;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourListViewModel implements Publisher<Tour> {

    private final ObservableList<Tour> tourList = FXCollections.observableArrayList();

    private final Observable<Tour> tourObservable = new Observable<>();

    @Override
    public void addListener(Listener<Tour> listener) {
        tourObservable.subscribe(listener);
    }

    @Override
    public void removeListener(Listener<Tour> listener) {
        tourObservable.unsubscribe(listener);
    }

    public void addTour(Tour tour) {
        tourList.add(tour);
    }

    public ObservableList<Tour> getTourList() {
        return tourList;
    }

    public ChangeListener<Tour> getChangeListener() {
        return ((observable, oldValue, newValue) -> tourObservable.notifyListeners(newValue));
    }
}
