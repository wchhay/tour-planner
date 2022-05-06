package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;

public class DashboardViewModel {

    private final TourViewModel tourViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;

    public DashboardViewModel(TourViewModel tourViewModel, TourDetailsViewModel tourDetailsViewModel) {
        this.tourViewModel = tourViewModel;
        this.tourDetailsViewModel = tourDetailsViewModel;

        this.tourViewModel.addSelectionChangedListener(this::selectTour);
    }

    private void selectTour(Tour tour) {
        tourDetailsViewModel.setTour(tour);
    }
}
