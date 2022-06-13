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

        this.tourListViewModel.subscribeToSelection(this::selectTour);
        this.tourDialogViewModel.subscribeToTourCreation(this::createTour);
        this.tourDialogViewModel.subscribeToTourUpdate(this::updateTour);
        this.tourListViewModel.subscribeToCreateTourClicked(this::resetCreationDialog);
    }

    private void resetCreationDialog(Boolean createTourClicked) {
        if (Boolean.TRUE.equals(createTourClicked)) {
            tourDialogViewModel.clearTour();
        }
    }

    private void selectTour(Tour tour) {
        if (null != tour) {
            tourDetailsViewModel.setTour(tour);
            logsViewModel.setLogsList(tour.getLogs());
            tourDialogViewModel.setTour(tour);
        }
    }

    private void createTour(Tour tour) {
        tourListViewModel.createTour(tour);
    }

    private void updateTour(Tour tour) {
        tourListViewModel.updateTour(tour);
    }

}
