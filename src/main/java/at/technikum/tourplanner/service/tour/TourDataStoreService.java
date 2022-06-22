package at.technikum.tourplanner.service.tour;

import at.technikum.tourplanner.dashboard.model.Tour;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;

public interface TourDataStoreService {
    void add(Tour tour);

    void remove(Tour tour);

    void update(Tour tour);

    void setTours(List<Tour> nextValue);

    void subscribe(ListChangeListener<Tour> listChangeListener);

    ObservableList<Tour> getTourObservableList();
}
