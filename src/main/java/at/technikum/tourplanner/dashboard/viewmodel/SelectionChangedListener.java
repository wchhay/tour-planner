package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;

public interface SelectionChangedListener {
    void changeSelection(Tour tour);
}
