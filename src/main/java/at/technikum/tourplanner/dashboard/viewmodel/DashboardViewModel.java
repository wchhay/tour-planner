package at.technikum.tourplanner.dashboard.viewmodel;


import at.technikum.tourplanner.dashboard.model.Tour;

public class DashboardViewModel {

    private final TourListViewModel tourListViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final TourDialogViewModel tourDialogViewModel;
    private final LogsViewModel logsViewModel;

    public DashboardViewModel(
            TourListViewModel tourListViewModel,
            TourDetailsViewModel tourDetailsViewModel,
            TourDialogViewModel tourDialogViewModel,
            LogsViewModel logsViewModel
    ) {
        this.tourListViewModel = tourListViewModel;
        this.tourDetailsViewModel = tourDetailsViewModel;
        this.tourDialogViewModel = tourDialogViewModel;
        this.logsViewModel = logsViewModel;

        this.tourListViewModel.addListener(this::selectTour);
        this.tourDialogViewModel.addListener(this::addTour);
    }

    private void selectTour(Tour tour) {
        tourDetailsViewModel.setTour(tour);
        logsViewModel.setLogsList(tour.getLogs());
    }

    private void addTour(Tour tour) {
        tourListViewModel.createTour(tour);
    }
}
