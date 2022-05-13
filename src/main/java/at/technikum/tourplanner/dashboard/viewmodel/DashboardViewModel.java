package at.technikum.tourplanner.dashboard.viewmodel;


import at.technikum.tourplanner.dashboard.model.Tour;

public class DashboardViewModel {

    private final TourListViewModel tourListViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final TourDialogViewModel tourDialogViewModel;

    public DashboardViewModel(
            TourListViewModel tourListViewModel,
            TourDetailsViewModel tourDetailsViewModel,
            TourDialogViewModel tourDialogViewModel
    ) {
        this.tourListViewModel = tourListViewModel;
        this.tourDetailsViewModel = tourDetailsViewModel;
        this.tourDialogViewModel = tourDialogViewModel;

        this.tourListViewModel.addListener(this::selectTour);
        this.tourDialogViewModel.addListener(this::addTour);
    }

    private void selectTour(Tour tour) {
        tourDetailsViewModel.setTour(tour);
    }

    private void addTour(Tour tour) {
        tourListViewModel.addTour(tour);
    }
}
