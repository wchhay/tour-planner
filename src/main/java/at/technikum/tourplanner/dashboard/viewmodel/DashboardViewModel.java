package at.technikum.tourplanner.dashboard.viewmodel;

import at.technikum.tourplanner.dashboard.model.Tour;

public class DashboardViewModel {

    private final TourListViewModel tourListViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;

    public DashboardViewModel(TourListViewModel tourListViewModel, TourDetailsViewModel tourDetailsViewModel) {
        this.tourListViewModel = tourListViewModel;
        this.tourDetailsViewModel = tourDetailsViewModel;

        this.tourListViewModel.addListener(this::selectTour);
    }

    private void selectTour(Tour tour) {
        tourDetailsViewModel.setTour(tour);
    }
}
