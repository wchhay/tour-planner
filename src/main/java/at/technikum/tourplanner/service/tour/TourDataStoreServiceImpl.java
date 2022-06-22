package at.technikum.tourplanner.service.tour;

import at.technikum.tourplanner.dashboard.model.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;

public class TourDataStoreServiceImpl implements TourDataStoreService {

    private final ObservableList<Tour> tourObservableList = FXCollections.observableArrayList();

    @Override
    public void add(Tour tour) {
        tourObservableList.add(tour);
    }

    @Override
    public void remove(Tour tour) {
        tourObservableList.remove(tour);
    }

    @Override
    public void update(Tour tour) {
        for (int i = 0; i < tourObservableList.size(); i++) {
            if (tourObservableList.get(i).getId().equals(tour.getId())) {
                tourObservableList.set(i, tour);
                return;
            }
        }
    }

    @Override
    public void setTours(List<Tour> nextValue) {
        tourObservableList.setAll(nextValue);
    }

    @Override
    public void subscribe(ListChangeListener<Tour> listChangeListener) {
        tourObservableList.addListener(listChangeListener);
    }

    @Override
    public ObservableList<Tour> getTourObservableList() {
        return tourObservableList;
    }
}
