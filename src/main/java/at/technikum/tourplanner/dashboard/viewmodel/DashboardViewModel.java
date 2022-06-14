package at.technikum.tourplanner.dashboard.viewmodel;


import at.technikum.tourplanner.dashboard.model.Log;
import at.technikum.tourplanner.dashboard.model.Tour;

public class DashboardViewModel {

    private final TourListViewModel tourListViewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final TourDialogViewModel tourDialogViewModel;
    private final LogsViewModel logsViewModel;
    private final LogCreationDialogViewModel logCreationDialogViewModel;

    public DashboardViewModel(
            TourListViewModel tourListViewModel,
            TourDetailsViewModel tourDetailsViewModel,
            TourDialogViewModel tourDialogViewModel,
            LogsViewModel logsViewModel,
            LogCreationDialogViewModel logCreationDialogViewModel
    ) {
        this.tourListViewModel = tourListViewModel;
        this.tourDetailsViewModel = tourDetailsViewModel;
        this.tourDialogViewModel = tourDialogViewModel;
        this.logsViewModel = logsViewModel;
        this.logCreationDialogViewModel = logCreationDialogViewModel;

        this.tourListViewModel.subscribeToSelection(this::selectTour);
        this.tourDialogViewModel.subscribeToTourCreation(this::createTour);
        this.tourDialogViewModel.subscribeToTourUpdate(this::updateTour);
        this.tourListViewModel.subscribeToCreateTourClicked(this::resetCreationDialog);
        this.logCreationDialogViewModel.subscribeToLogCreation(this::createLog);

    }

    private void createLog(Log log) {
        logsViewModel.createLog(log);
    }

    private void resetCreationDialog(Boolean createTourClicked) {
        if (Boolean.TRUE.equals(createTourClicked)) {
            tourDialogViewModel.clearTour();
        }
    }

    private void selectTour(Tour tour) {
        if (null != tour) {
            tourDetailsViewModel.setTour(tour);
            logsViewModel.setSelectedTour(tour);
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
